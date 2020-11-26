package com.test.movierent.controller;


import com.test.movierent.config.MessageProvider;
import com.test.movierent.exception.NotCreatedException;
import com.test.movierent.exception.NotExistException;
import com.test.movierent.exception.ParameterException;
import com.test.movierent.model.Movie;
import com.test.movierent.model.MovieUserRentBuy;
import com.test.movierent.model.dto.MovieDto;
import com.test.movierent.model.dto.MovieRentBuyResponse;
import com.test.movierent.model.dto.MovieResponse;
import com.test.movierent.service.UserMovieService;
import com.test.movierent.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    private MovieService movieService;


    @Value("${application.default-page-size}")
    private Integer DEFAULT_PAGE_SIZE;

    @Autowired
    private UserMovieService userMovieService;

    @Autowired
    private MessageProvider messageProvider;

    @Value("${application.default-days-rent-movie}")
    private Integer DEFAULT_DAYS_RENT;

    @Value("${application.default-pay-late-return}")
    private Double DEFAULT_PAY_LATE;

    /** Create a new Movie
     * @param movie contains image property, this must be byte array
     **/
    @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> createMovie(@RequestBody MovieDto movie) throws IOException {
        if (movie.getTittle() == null){
            throw new ParameterException("{tittle} can not be null");
        }
        Movie newMovie = new Movie();
        BeanUtils.copyProperties(movie, newMovie);
        Movie created = movieService.save(newMovie);
        if (created == null){
            throw new NotCreatedException("Movie :" + movie.getTittle() + "Can not be created");
        }else{
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    // Update movie by id
    @PutMapping("/{movieId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> updateMovie(@PathVariable Long movieId, @RequestBody MovieDto movie)
            throws NotCreatedException, ParameterException {
        if (movie.getTittle() == null){
            throw new ParameterException("{tittle} can not be null");
        }
        if (!movieService.updateById(movieId, movie)){
            throw new NotCreatedException("Movie :" + movie.getTittle() + "Can not be updated");
        }else{
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @DeleteMapping("/{movieId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> deleteMovie(@PathVariable Long movieId)
            throws NotCreatedException, ParameterException {
        movieService.deleteById(movieId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Method for update the availability of any movie
     **/
    @PatchMapping("/{movieId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> updateAvailability(@PathVariable Long movieId,
                                                @RequestBody Boolean availability)
            throws NotCreatedException {
        if (availability == null) {
            throw new ParameterException("Parameter {availability} can not be null");
        }
        movieService.updateAvailabilityById(movieId, availability);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Method without authentication, permit get all movies when availability is true
     * Permit pagination {total_elements, total_pages} are returned for functionality
     **/
    @GetMapping("/")
    public ResponseEntity<MovieResponse> getAll(HttpServletRequest request,
                                                  @RequestParam(name = "page_num", required = false, defaultValue = "0") Integer pageNum,
                                                  @RequestParam(name = "page_size", required = false) Integer pageSize,
                                                  @RequestParam(name = "sort_fields", required = false, defaultValue = "tittle") String[] sortFields,
                                                  @RequestParam(name = "sort_order", required = false) String sortOrder){
        pageSize = pageSize==null?DEFAULT_PAGE_SIZE:pageSize;
        int newPageNum = pageNum >= 1?pageNum-1:pageNum;
        Pageable pageable = PageRequest.of(newPageNum, pageSize, getSort(sortOrder, sortFields));
        Page<Movie> moviePage = movieService.findAllByAvailability(true, pageable);
        return new ResponseEntity<>(getMovieResponseFromPage(moviePage), HttpStatus.OK);
    }

    /**
     * Method with authentication and just admin role, permit get all movies when availability is true or false
     * Permit pagination, filter and sort
     **/
    @GetMapping("/admin/")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> getAllAdmin(HttpServletRequest request,
                                         @RequestParam(name = "page_num", required = false, defaultValue = "0") Integer pageNum,
                                         @RequestParam(name = "page_size", required = false) Integer pageSize,
                                         @RequestParam(name = "sort_fields", required = false, defaultValue = "tittle") String[] sortFields,
                                         @RequestParam(name = "sort_order", required = false) String sortOrder,
                                         @RequestParam(name = "availability", required = false) Integer availability){
        Pageable pageable = PageRequest.of(pageNum >= 1?pageNum-1:pageNum,
                pageSize==null?DEFAULT_PAGE_SIZE:pageSize,
                getSort(sortOrder, sortFields));
        Page<Movie> allMovies;
        if (availability == null){
            allMovies = movieService.findAll(pageable);
        }else{
            allMovies = movieService.findAllByAvailability(availability == 1, pageable);
        }
        return new ResponseEntity<>(getMovieResponseFromPage(allMovies), HttpStatus.OK);
    }

    @GetMapping("/{movieID}")
    public ResponseEntity<?> getDetail(@PathVariable("movieID") Long movieID){
        Movie movie = movieService.findByMovieId(movieID);
        if (movie == null){
            throw new NotExistException("The movie: "+movieID+" does not exist");
        }
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

    // Like funcionality by username in security
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PutMapping("/{movieID}/like")
    public ResponseEntity<?> likeMovie(@PathVariable("movieID") Long movieID){
        userMovieService.like(movieID);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    // Rent a movie by any user
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping("/{movieID}/rent")
    public ResponseEntity<?> rentMovie(@PathVariable("movieID") Long movieID, @RequestParam(name = "quantity") Integer quantity){
        MovieUserRentBuy movieRent = userMovieService.rent(movieID, quantity);
        logger.info("A movie was rent "+ movieRent.getMovie().getTittle() + " to time: " + movieRent.getRentDate());
        MovieRentBuyResponse response = new MovieRentBuyResponse();
        BeanUtils.copyProperties(movieRent, response);
        response.setMessage(messageProvider.getWarningMovieRent());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Return a movie rental by any user
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping("/{movieID}/return")
    public ResponseEntity<?> returnRentMovie(@PathVariable("movieID") Long movieID){
        MovieUserRentBuy movieReturn = userMovieService.findByUserAndMovieId(movieID);
        MovieRentBuyResponse response = new MovieRentBuyResponse();
        BeanUtils.copyProperties(movieReturn, response);
        response.setMessage("The movie was returned successfully");
        if ( LocalDateTime.now().compareTo(movieReturn.getRentDate()) > DEFAULT_DAYS_RENT) {
            movieReturn.setPayLateReturn(new BigDecimal(DEFAULT_PAY_LATE));
            response.setPayLateReturn(new BigDecimal(DEFAULT_PAY_LATE));
            response.setMessage("An payment was added for return late the movie");
        }
        userMovieService.returnMovie(movieReturn);
        logger.info("A movie was returned "+ movieReturn.getMovie().getTittle() + " to time: " + LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Buy a movie by any user
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping("/{movieID}/buy")
    public ResponseEntity<?> buyMovie(@PathVariable("movieID") Long movieID, @RequestParam(name = "quantity")  Integer quantity){
        MovieUserRentBuy movieBuy = userMovieService.buy(movieID, quantity);
        logger.info("A movie was buy "+ movieBuy.getMovie().getTittle() + " to time: " + movieBuy.getRentDate());
        MovieRentBuyResponse response = new MovieRentBuyResponse();
        BeanUtils.copyProperties(movieBuy, response);
        response.setMessage(messageProvider.getMessageMovieBuy());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // Wait for a param named {name} for realize a search
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam("name") String nameMovie ){
        Movie movie = movieService.findByNameMovie(nameMovie);
        if (movie == null){
            throw new NotExistException("The movie: "+nameMovie+" does not exist");
        }
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

    // A util method for create a sort from params
    private Sort getSort(String sortOrder, String[] sortFields){
        Sort sort;
        if (Sort.Direction.ASC.name().equalsIgnoreCase(sortOrder)){
            sort = Sort.by(sortFields).ascending();
        }else{
            sort = Sort.by(sortFields).descending();
        }
        return sort;
    }

    // Get a movieResponse from Page with data
    MovieResponse getMovieResponseFromPage(Page<Movie> moviePage){
        MovieResponse response = new MovieResponse();
        response.setMovies(moviePage.getContent());
        response.setTotalElements(moviePage.getTotalElements());
        response.setTotalPages(moviePage.getTotalPages());
        response.setPageSize(moviePage.getSize());
        response.setPageNum(moviePage.getNumber()+1);
        response.setCount(moviePage.getNumberOfElements());
        return response;
    }


}
