package com.teamtreehouse.techdegree.overboard.model;

import com.teamtreehouse.techdegree.overboard.exc.AnswerAcceptanceException;
import com.teamtreehouse.techdegree.overboard.exc.VotingException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

/**
 * Created by Haley on 5/21/17.
 */
public class UserTest {
    private User user;
    private User newUser;
    private Board board;
    private Question question;
    private Answer answer;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        board = new Board("Board Topic");
        user = new User(board, "Haley");
        newUser = new User(board, "John");
        question = user.askQuestion("This is my question?");
        answer = newUser.answerQuestion(question, "This is my answer");

    }

    @Test
    public void reputationGoesUpIfQuestionIsUpVoted() throws Exception {
        newUser.upVote(question);

        assertEquals(5, user.getReputation());

    }

    @Test
    public void answererUpVoteGoesUpByTenPoints() throws Exception {
        user.upVote(answer);

        assertEquals(10, newUser.getReputation());
    }

    @Test
    public void answerAcceptedAddsToReputation() throws Exception {
        user.acceptAnswer(answer);

        assertEquals(15, newUser.getReputation());
    }

    @Test
    public void userCannotUpVoteTheirQuestion() throws Exception {
        thrown.expect(VotingException.class);
        thrown.expectMessage("You cannot vote for yourself!");

        user.upVote(question);
    }

    @Test
    public void userCannotDownVoteTheirQuestion() throws Exception {
        thrown.expect(VotingException.class);
        thrown.expectMessage("You cannot vote for yourself!");

        user.downVote(question);
    }


    @Test
    public void userCannotUpVoteTheirAnswer() throws Exception {
        thrown.expect(VotingException.class);
        thrown.expectMessage("You cannot vote for yourself!");

        newUser.upVote(answer);
    }

    @Test
    public void userCannotDownVoteTheirAnswer() throws Exception {
        thrown.expect(VotingException.class);
        thrown.expectMessage("You cannot vote for yourself!");

        newUser.downVote(answer);
    }

    @Test
    public void originalQuestionerAcceptsAnswer() throws Exception {
        thrown.expect(AnswerAcceptanceException.class);
        thrown.expectMessage("Only Haley can accept this answer as it is their question");

        newUser.acceptAnswer(answer);
    }

}