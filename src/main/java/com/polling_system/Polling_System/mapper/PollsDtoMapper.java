package com.polling_system.Polling_System.mapper;

import com.polling_system.Polling_System.dto.OptionVoteDto;
import com.polling_system.Polling_System.dto.PollsDto;
import com.polling_system.Polling_System.models.OptionVote;
import com.polling_system.Polling_System.models.Polls;

import java.util.List;
import java.util.stream.Collectors;




public class PollsDtoMapper {


    public static  PollsDto pollsToPollsDto(Polls polls){
        List<OptionVoteDto> optionVoteDtos = polls.getOptions().stream()
                .map(optionVote -> new OptionVoteDto(
                        optionVote.getVoteOption(),
                        optionVote.getVoteCount()
                ))
                .collect(Collectors.toList());
        return new PollsDto(
                polls.getId(),
                polls.getQuestion(),
                optionVoteDtos
        );
    }


    public static Polls pollsDtoToPolls(PollsDto pollDto){
        List<OptionVote> options = pollDto.options().stream()
                .map(dto -> new OptionVote(dto.voteOption(), dto.voteCount()))
                .collect(Collectors.toList());
        return new Polls(
                pollDto.id(),
                pollDto.question(),
               options
        );
    }
}