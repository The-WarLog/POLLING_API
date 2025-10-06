package com.polling_system.Polling_System.services;

import com.polling_system.Polling_System.dto.PollsDto;
import com.polling_system.Polling_System.models.Polls;
import com.polling_system.Polling_System.models.Vote;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface PollsServices  {
    public PollsDto createPolls(Polls  polls);
    public List<PollsDto> getAllPolls();
    public PollsDto getPollsById(Long id);
    public boolean deletePolls(Long id);
    public PollsDto updatePolls(Long id, Polls polls);
    //public void updateVote(Long id, int OptionVoteIndex);
    PollsDto addVote(Long pollId, String voteOption);

}
