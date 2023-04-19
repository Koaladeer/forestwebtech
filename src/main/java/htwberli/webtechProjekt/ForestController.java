package htwberli.webtechProjekt;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ForestController {
        @GetMapping("/greeting")
        public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name) {
            return "hello, " + name;
            
        }
        @GetMapping("/")
        public String root() {
            return "hello";
        }
        @GetMapping("/chat")
        public String chat(Model model) {
            return "chat";
    }
    }
