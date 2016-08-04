package app.horoscope.calculator;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.awt.geom.Area;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeParseException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.NestedServletException;

import app.horoscope.calculator.controllers.HoroscopeController;
import app.horoscope.calculator.entities.Aries;
import app.horoscope.calculator.tools.HoroscopeHelper;
import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
//@ComponentScan(basePackages="app.*")
@SpringApplicationConfiguration(classes = App.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class HoroscopeControllerTest extends TestCase {

	@Value("${local.server.port}")
	int port;

	private URL base;
	private RestTemplate template;
	private MockMvc mvc;
	
	// must be autowired, otherwise all autowired stuff in the controller will be ignore
	// since we are creating a new instance with new keyword
	@Autowired
	private HoroscopeController horoscopeController;
	
	@Before
	public void setUp() throws Exception {
		//super.setUp();
		mvc = MockMvcBuilders.standaloneSetup(horoscopeController).build();
		this.base = new URL("http://localhost:" + port + "/horoscope-calculator");
		template = new TestRestTemplate();
	}
	
	@Test
	@Ignore
	public void checkHoroscopeWithoutAnyValues(){
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		assertThat(response.getBody(), equalTo("test"));
	}
	
	@Test
//	@Ignore
	public void checkHoroscopeOnlyWithDate() throws Exception{
		mvc.perform(MockMvcRequestBuilders.get("/horoscope-calculator?date=1990-03-30").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().json("{\"date\":\"1990-03-30\",\"horoscopeSign\":\"Aries\",\"risingHoroscopeSign\":\"\"}"));
	}

	@Test
	public void checkHoroscopeWithDateAndTime() throws Exception{
		mvc.perform(MockMvcRequestBuilders.get("/horoscope-calculator?date=1990-03-30T12:10").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().json("{\"date\":\"1990-03-30T12:10\",\"horoscopeSign\":\"Aries\",\"risingHoroscopeSign\":\"Leo\"}"));
	}
	
	@Test(expected=NestedServletException.class)
	public void checkHoroscopeWithDateAndTimeWrongFormat() throws Exception{
		mvc.perform(MockMvcRequestBuilders.get("/horoscope-calculator?date=1990-03-30T12").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().is(500));
//		.andExpect(content().json("{\"date\":\"\",\"horoscopeSign\":\"\",\"risingHoroscopeSign\":\"\"}"));
	}
	
	@Test(expected=NestedServletException.class)
	public void checkHoroscopeWithDateWrongFormat() throws Exception{
		mvc.perform(MockMvcRequestBuilders.get("/horoscope-calculator?date=1990-03").accept(MediaType.APPLICATION_JSON));
//		.andExpect(status().isOk())
//		.andExpect(content().json("{\"date\":\"\",\"horoscopeSign\":\"Aries\",\"risingHoroscopeSign\":\"Aquarius\"}"));
	}
	
//	@Test
//	public void testHoroscopeRisingSign(){
//		Aries aries = new Aries();
//		assertTrue(aries.getRisingSign("12.10").equals("LEO")); 
//	}
//	
	
//	@Test
//	public void testParseCSV() throws IOException{
//		HoroscopeHelper hlp = new HoroscopeHelper();
//		hlp.parseCSV();
//	}
	
	@Test
	public void testRisingSingHoroscopeAriesCancer() throws IOException{
		HoroscopeHelper hlp = new HoroscopeHelper();
		assertTrue(hlp.findRisingSignHoroscope("1990-03-30T10:22").equals("Cancer"));	
	}
	
	@Test
	public void testRisingSingHoroscopeAriesLeo() throws IOException{
		HoroscopeHelper hlp = new HoroscopeHelper();
		assertTrue(hlp.findRisingSignHoroscope("1990-03-30T12:10").equals("Leo"));	
	}
}
