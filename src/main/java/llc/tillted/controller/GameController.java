package llc.tillted.controller;

import llc.tillted.model.Game;
import llc.tillted.model.requests.GamesWithinRadiusRequest;
import llc.tillted.service.GameService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/game")
public class GameController {

    private GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

//    @GetMapping("/test")
//    public void test() {
//        gameService.test();
//    }

    @GetMapping("/withinRadius")
    public List<Game> gamesWithinRadius(@RequestBody GamesWithinRadiusRequest request) {
        return gameService.gamesWithinRadius(request);
    }

}
