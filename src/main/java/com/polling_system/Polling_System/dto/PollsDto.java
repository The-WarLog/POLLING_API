package com.polling_system.Polling_System.dto;

import com.polling_system.Polling_System.models.OptionVote;

import java.util.List;

public record PollsDto (

        Long id,
        String question,

        List<OptionVoteDto> options


){}
