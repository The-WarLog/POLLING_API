package com.polling_system.Polling_System.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "polls")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Polls {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //this is the primary key
    private String question;
    @ElementCollection
    @CollectionTable(
            name="poll_options",
            joinColumns = @JoinColumn(name="poll_id")
    )
    private List<OptionVote> options=new ArrayList<>();
//    @ElementCollection
//    private List<Long>  votes=new  ArrayList<>();
}
