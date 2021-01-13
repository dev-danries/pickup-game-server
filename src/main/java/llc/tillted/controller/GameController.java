package llc.tillted.controller;

import llc.tillted.model.Game;
import llc.tillted.model.requests.GamesWithinRadiusRequest;
import llc.tillted.service.GameService;
import llc.tillted.swagger.DocumentSwagger;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game")
@DocumentSwagger
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/withinRadius")
    public List<Game> gamesWithinRadius(@RequestBody GamesWithinRadiusRequest request) {
        return gameService.gamesWithinRadius(request);
    }

}
