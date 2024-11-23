package server;

public class ServerProtocol {

    private QuizGame game;
    final protected int WAIT = 0;
    final protected int CATEGORY = 1;
    final protected int QUESTION = 2;

    protected int state = WAIT;

    public ServerProtocol(QuizGame game) {
        this.game = game;
    }

    public String getOutput(String input) {
        if (state == WAIT) {

        } else if (state == CATEGORY) {

        } else if (state == QUESTION) {

        }
        return "unexpected error";
    }
}
