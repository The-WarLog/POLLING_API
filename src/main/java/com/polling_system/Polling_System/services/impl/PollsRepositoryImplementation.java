package com.polling_system.Polling_System.services.impl;

import com.polling_system.Polling_System.dto.PollsDto;
import com.polling_system.Polling_System.exceptions.ResourceNotFoundException;
import com.polling_system.Polling_System.mapper.PollsDtoMapper;
import com.polling_system.Polling_System.models.OptionVote;
import com.polling_system.Polling_System.models.Polls;
import com.polling_system.Polling_System.repository.PollsRepository;
import com.polling_system.Polling_System.services.PollsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PollsRepositoryImplementation implements PollsServices {
    @Autowired
    private PollsRepository pollsRepository;


    @Override
    @Transactional
    public PollsDto createPolls(Polls polls) {
    //Polls polls1= PollsDtoMapper.pollsDtoToPolls(polls);
    pollsRepository.save(polls);
    return PollsDtoMapper.pollsToPollsDto(polls);


    }

    @Override
    @Transactional(readOnly = true)
    public List<PollsDto> getAllPolls() {
       List<Polls> polls=pollsRepository.findAll();
       return polls.stream().map(PollsDtoMapper::pollsToPollsDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PollsDto getPollsById(Long id) {
        Polls polls=pollsRepository.findById(id).orElse(null);
        return PollsDtoMapper.pollsToPollsDto(polls);

    }

    @Override
    @Transactional
    public boolean deletePolls(Long id) {
      if(pollsRepository.existsById(id)){
          pollsRepository.deleteById(id);
          return true;
      }
      return false;
    }
//review this function
@Override
@Transactional
public PollsDto updatePolls(Long id, Polls updatedPolls) {
    Polls exitingpoll=pollsRepository.findById(id).orElseThrow(()->new RuntimeException("Poll not found"));
    if(updatedPolls.getQuestion()!=null){
        exitingpoll.setQuestion(updatedPolls.getQuestion());
    }
    if(updatedPolls.getOptions()!=null){
        exitingpoll.setOptions(updatedPolls.getOptions());
        exitingpoll.getOptions().clear();
        //addng the new ones i have no idea why do i have to clear them
        exitingpoll.getOptions().addAll(updatedPolls.getOptions());
    }
    Polls savednewone=pollsRepository.save(exitingpoll);
    return  PollsDtoMapper.pollsToPollsDto(savednewone);

}



   /* @Override
    @Transactional
    public void updateVote(Long id, int OptionVoteIndex) {
       Polls vote1=pollsRepository.findById(id).orElseThrow(()->new RuntimeException("Vote not found"));
       //first check if the index in valid or not
        if(OptionVoteIndex<0 || OptionVoteIndex>=vote1.getOptions().size()){
            throw new RuntimeException("Invalid option index");
        }
        //geth options to to edit for
       vote1.getOptions().get(OptionVoteIndex).setVoteCount(vote1.getOptions().get(OptionVoteIndex).getVoteCount()+1);
        //update the vote
        pollsRepository.save(vote1);
    }*/
   @Override
   @Transactional
   public PollsDto addVote(Long pollId, String voteOption){
       Polls poll = pollsRepository.findById(pollId)
               .orElseThrow(() -> new ResourceNotFoundException("Poll not found with id: " + pollId));
       OptionVote optionToUpdate = poll.getOptions().stream()
               .filter(option -> voteOption.equals(option.getVoteOption()))
               .findFirst()
               .orElseThrow(() -> new ResourceNotFoundException(
                       "Option '" + voteOption + "' not found in poll with id: " + pollId
               ));
       optionToUpdate.setVoteCount(optionToUpdate.getVoteCount() + 1);
       return PollsDtoMapper.pollsToPollsDto(poll);


   }
}
