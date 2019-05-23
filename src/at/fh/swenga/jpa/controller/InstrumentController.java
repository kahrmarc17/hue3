package at.fh.swenga.jpa.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import at.fh.swenga.jpa.dao.ManufacturerRepository;
import at.fh.swenga.jpa.dao.InstrumentRepository;
import at.fh.swenga.jpa.model.ManufacturerModel;
import at.fh.swenga.jpa.model.InstrumentModel;

@Controller
public class InstrumentController {

	@Autowired
	InstrumentRepository instrumentRepository;
	
	@Autowired
	ManufacturerRepository manufacturerRepository;

	@RequestMapping(value = { "/", "list" })
	public String index(Model model) {
		List<InstrumentModel> instruments = instrumentRepository.findAll();
		model.addAttribute("instruments", instruments);
		model.addAttribute("count", instruments.size());
		return "index";
	}
	
	@RequestMapping(value = { "/getPage" })
	public String getPage(Pageable page,Model model) {
		Page<InstrumentModel> instrumentsPage = instrumentRepository.findAll(page);
		model.addAttribute("instrumentsPage", instrumentsPage);
		model.addAttribute("instruments", instrumentsPage.getContent());
		model.addAttribute("count", instrumentsPage.getTotalElements());
		return "index";
	}

	@RequestMapping(value = { "/find" })
	public String find(Model model, @RequestParam String searchString, @RequestParam String searchType) {
		List<InstrumentModel> instruments = null;
		int count=0;

		switch (searchType) {
		case "query1":
			instruments = instrumentRepository.findAll();
			break;
		case "query2":
			instruments = instrumentRepository.findByCategory(searchString);
			break;
		case "query3":
			instruments = instrumentRepository.findByCategoryOrName(searchString, searchString);
			break;
		case "query4":
			instruments = instrumentRepository.findByAnyName(searchString);
			break;
		case "query5":
			instruments = instrumentRepository.findByNamedQuery("%"+searchString+"%");
			break;
		case "query6":
			instruments = instrumentRepository.findTop15ByOrderByName();
			break;
		case "query7":
			count = instrumentRepository.countByCategory(searchString);
			break;
		case "query8":
			instruments = instrumentRepository.deleteByName(searchString);
			break;
		case "query9":
			count = instrumentRepository.deleteByManufacturerName(searchString);
			break;
		case "query10":
			instruments = instrumentRepository.findByManufacturerNameOrderByNameAsc(searchString);
			break;
		case "query11":
			instruments = instrumentRepository.findByCategoryContainingOrNameContainingAllIgnoreCase(searchString,searchString);
			break;	
		

		default:
			instruments = instrumentRepository.findAll();
		}
		
		model.addAttribute("instruments", instruments);

		if (instruments!=null) {
			model.addAttribute("count", instruments.size());			
		}
		else {
			model.addAttribute("count", count);				
		}
		return "index";
	}


	@RequestMapping(value = { "/findById" })
	public String findById(@RequestParam("id") InstrumentModel i, Model model) {
		if (i!=null) {
			List<InstrumentModel> instruments = new ArrayList<InstrumentModel>();
			instruments.add(i);
			model.addAttribute("instruments", instruments);
		}
		return "index";
	}

	
	@RequestMapping("/fillInstrumentList")
	@Transactional
	public String fillData(Model model) {

		DataFactory df = new DataFactory();
		
		ManufacturerModel manufacturer = null;
		
		for(int i=0;i<80;i++) {
			if (i%10==0) {
				String[] manufacturers= {"Ibanez", "Clover", "Jackson", "Greg Bennet", "Musica", "Cerveny", "Sonor", "Yamaha"};
				String manufacturerName = df.getItem(manufacturers);
				manufacturer=manufacturerRepository.findFirstByName(manufacturerName);
				if (manufacturer==null) {
					manufacturer = new ManufacturerModel(manufacturerName);
				}
			}
			
			
			LocalDate start = LocalDate.now().minusMonths(5).with(TemporalAdjusters.lastDayOfMonth());
			LocalDate end = LocalDate.now().plusMonths(5).with(TemporalAdjusters.lastDayOfMonth());
			 
			//Create stream of dates
			List<LocalDate> dates = Stream.iterate(start, date -> date.plusDays(1))
			                            .limit(ChronoUnit.DAYS.between(start, end))
			                            .collect(Collectors.toList());
			 
			// Get Min or Max Date
			LocalDate maxDate = dates.stream()
			                            .max( Comparator.comparing( LocalDate::toEpochDay ) )
			                            .get();
			 
			LocalDate minDate = dates.stream()
			                            .min( Comparator.comparing( LocalDate::toEpochDay ) )
			                            .get();
			
			Date mindatenew = java.sql.Date.valueOf(minDate);
			Date maxdatenew = java.sql.Date.valueOf(maxDate);
		
			  
			String[] categories= {"Trompete", "Schlagzeug", "Posaune", "Klarinette", "E-Gitarre", "Ukulele", "E-Pianos", "Keyboards", "Querflöte", "Gitarre", "Piccolo-Flöte", "Tenorhorn", "Bariton", "Saxophone", "Euphonium", "Flügelhorn", "Pauken", "Trommeln", "Schlagzeugzubehör", "Horn", "Tuba"};
			String categoryName = df.getItem(categories);
				
			
			String[] letter1 = {"A", "C", "G", "W", "R", "E", "K", "O", "S", "T", "H"};
			String[] letter2 = {"C", "D", "I", "P","E", "U", "O", "L", "M", "H"};
			String[] letter3 = {"X", "D", "A", "W","E", "Q", "S", "L", "B", "H"};
			String[] space = {" "};
			String[] numbers = {"35", "353", "94", "89","3785", "494", "120", "12", "01", "8382", "23", "35", "102", "25", "68", "948", "2", "7"};
			String[] names= {" DW", " Mainstage", " BK", " PDP"," G", " SCL", " BS", " "};
			String instrumentName = df.getItem(letter1);
			String instrumentName2 = df.getItem(letter2);
			String instrumentName3 = df.getItem(letter3);
			String instrumentName4 = df.getItem(space);
			String instrumentName5 = df.getItem(numbers);
			String instrumentName6 = df.getItem(names);
			
			String[] descriptions= {"Zum ersten Mal hat der Hersteller ein Instrument mit einem reversed Type Hauptstimmzug entwickelt, was sie in Sachen Spielgefuehl und Ansprache einmalig macht.", "Das Produkt erfuellt die Wuensche von jedem Kunden. Es ist leicht in der Handhabung und erfreut Groß und Klein beim Musizieren.", "mit Quartventil, Goldmessing-Schallstueck, L-Bohrung, inkl. Koffer und Mundstueck", "Grenadillholzkorpus, zahlreiche Klappen und Ringe, versilberte Klappen, Birne 56mm, inkl. Etui mit Rucksackgarnitur, Mundstueck und Pflegeset", "Agathis Korpus, Ahorn Hals, 22 Medium Buende, Palisander Griffbrett, Farbe: Brown Sunburst", "Concert size, Nato top, back, sides and neck, Ebonized fingerboard, Geared tuners", "Digitaler Stutzfluegel fuer den Wohnraum. Topaktuelle SuperNATURAL Modelingtechnologie fuer ein lebendiges Klangerlebnis.", "anschlagdynamische Tasten, 433 Styles, 128 stimmig Polyphonie, DJ Styles", "Klangvolles Instrument bei dem jeder zum Staunen kommt. Das edle Design, der wunderschöne Glanz sowie die Leichtigkeit in der Handhabung ist atemberaubend.", "Sehr leicht im Vergleich zu andere Modelle. Ideal für Beginner geeignet. Sehr flexibel und verstellbar.", "Dieses Produkt ist die Königsklasse und verleiht jedem der es spielt ein Gefühl der Freude."};
			String description = df.getItem(descriptions);
			
			InstrumentModel instrumentModel = new InstrumentModel(categoryName, instrumentName + instrumentName2 + instrumentName3 + instrumentName4 + instrumentName5 + instrumentName6, description, df.getNumberBetween(259, 3567), df.getDateBetween(mindatenew, maxdatenew), df.getNumberBetween(1, 15), df.getNumberBetween(1, 10));  
			instrumentModel.setManufacturer(manufacturer);
			instrumentRepository.save(instrumentModel);
		
		}
		
		return "forward:list";
	}

	@RequestMapping("/delete")
	public String deleteData(Model model, @RequestParam int id) {
		instrumentRepository.deleteById(id);

		return "forward:list";
	}

	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {

		return "error";

	}
}
