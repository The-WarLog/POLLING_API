package com.polling_system.Polling_System.controllers;
import com.polling_system.Polling_System.dto.PollsDto;
import com.polling_system.Polling_System.mapper.PollsDtoMapper;
import com.polling_system.Polling_System.models.Polls;
import com.polling_system.Polling_System.models.Vote;
import com.polling_system.Polling_System.services.PollsServices;
import com.polling_system.Polling_System.services.impl.PollsRepositoryImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/polls")
public class PollsController {
    @Autowired
    PollsServices pollsServices;

    //get method for fetching the polls
    @GetMapping("/")
    public ResponseEntity<List<PollsDto>> getallthepolls(){
        List<PollsDto> polls=pollsServices.getAllPolls();

        return ResponseEntity.ok(polls);
    }
    //@GetMapping("/getpoll/{id}")
    @GetMapping("/getpoll/{id}")
    public  ResponseEntity<PollsDto> getpoll(@PathVariable Long id){
        PollsDto pollsDto=pollsServices.getPollsById(id);
        return  ResponseEntity.ok(pollsDto);
    }
    //@Post method for creating the polls
    @PostMapping("/createpoll")
    public ResponseEntity<?> createPolls(@RequestBody Polls polls){
       PollsDto polls1= pollsServices.createPolls(polls);
        return  ResponseEntity.ok(polls1);
    }
    //@Delete Method for deleting the particular poll
    @DeleteMapping("/deletepoll/{id}")
    public ResponseEntity<?> deletePolls(@PathVariable Long id){
        boolean deleted= pollsServices.deletePolls(id);
        return ResponseEntity.ok(deleted);

    }
    //@Put method for updating the particular poll
    @PutMapping("/updatepoll/{id}")
    public ResponseEntity<PollsDto> updatePolls(@PathVariable Long id, @RequestBody Polls polls){
        PollsDto pollsDto=pollsServices.updatePolls(id,polls);
        if(pollsDto!=null){
            return ResponseEntity.ok(pollsDto);
        }else{
            return ResponseEntity.notFound().build();
        }

    }
    // FOR SOME REASON THIS IS WORKING
    @PatchMapping("/{pollId}/vote")
    public ResponseEntity<PollsDto> addVote(@PathVariable Long pollId, @RequestBody Map<String, String> payload) {
        String voteOption = payload.get("voteOption");
        if (voteOption == null || voteOption.isBlank()) {
            // Return a 400 Bad Request error if the JSON is missing the required key
            return ResponseEntity.badRequest().build();
        }
        PollsDto updatedPoll = pollsServices.addVote(pollId, voteOption);
        return ResponseEntity.ok(updatedPoll);
    }
}
