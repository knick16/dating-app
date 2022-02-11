package dating.controllers;

import dating.domain.ConversationService;
import dating.domain.Result;
import dating.models.Conversation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/user/conversation")
public class ConversationController {

    // Field
    ConversationService conversationService;

    // Constructor
    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    // Method: Find conversations for a user to load in their conversation list.
    @GetMapping
    public ResponseEntity<List<Conversation>> getConversationByFriend(Principal principal){

        List<Conversation> conversations = conversationService.findByUsername(principal.getName());
        if (conversations == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(conversations, HttpStatus.OK);
    }

    // Method: Find a specific conversation to load the attached content.
    @GetMapping("/{conversationId}")
    public ResponseEntity<Conversation> getConversationById(Principal principal, @PathVariable int conversationId) {
        Conversation conversation = conversationService.findByConversationId(principal.getName(), conversationId);
        if (conversation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(conversation, HttpStatus.OK);
    }
}
