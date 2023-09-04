package be.boets.addressbook.search;

import be.boets.addressbook.dto.SearchCriteria;
import be.boets.addressbook.dto.PersonDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public List<PersonDto> search(SearchCriteria searchCriteria) {
        return searchService.search(searchCriteria);
    }
}
