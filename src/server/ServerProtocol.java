package server;

public class ServerProtocol {

    private QuizGame game;
//    final protected int WAIT = 0;
    final protected int CATEGORY = 0;
    final protected int QUESTION = 0;

    protected int state = CATEGORY;

    public ServerProtocol(QuizGame game) {
        this.game = game;
    }

    public String getOutput(String input) {

//        if (input.startsWith("category chosen")) {
//            System.out.println(activePlayer.getName() + " " + input);
//            activePlayer.send("QUESTION");
//        } else if (input.startsWith("question answered")) {
//            System.out.println(activePlayer.getName() + " " + input);
//            changeActivePlayer();
////                    activePlayer.send("CATEGORY");
//        }
        if (state == CATEGORY) {

        } else if (state == QUESTION) {

        }
        return "unexpected error";
    }
}
