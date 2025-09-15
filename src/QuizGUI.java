import javax.swing.border.Border; 
import javax.swing.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;


public class QuizGUI extends LoadQuestions{

    // == Quiz state variables ===
    private static List<Question> questions;
    private static int currentQuestion = 1;
    private static int correctQuestions ;
    private static int totalQuestions = 20;
    private static Question currentQ;
    private static boolean sound=true;
    private static int counter;
    
    
    // === GUI components ===
    private static JLabel endLabel;                     // End of quiz message
    private static JLabel resultLabel;                  // Feedback (correct/wrong)
    private static JLabel questionCounterLabel;         // Counter of question (1/20 Ερωτηση)
    private static Timer endMessageTimer;               // Timer for final score message
    //private static JLabel qLabel;                       // Question label
    private static javax.swing.Timer clearMessageTimer;
    private static JLabel clockLabel;
    private static Timer clockTimer;
    private static JLabel successLabel;
    private static JTextArea qLabel;

    
// ========== Methods ==========



/**
Display a random question from the list.
If the quiz has reached 20 questions, display "END".
*/
private static void showRandomQuestion(int currentQuestion){
            Random random= new Random();
            int randomNum= random.nextInt(questions.size());
            currentQ = questions.get(randomNum);
            if (currentQuestion<=20){
                qLabel.setText( currentQ.question );
            }else{
                qLabel.setText("END");
            }
            

        }


/*
Check the user's answer and update score/labels.
Plays sounds and shows final message when quiz ends.
*/
private static void checkAnswer(boolean userAnswer){
    


 //Special case: last question
 if (currentQuestion == 20) {
    if (currentQ.answer == userAnswer) {
        
        correctQuestions++;
        playCorrectSound();
        resultLabel.setText("ΑΠΑΝΤΗΣΕΣ ΣΩΣΤΑ");
          if (clearMessageTimer != null && clearMessageTimer.isRunning()) {
            clearMessageTimer.stop();
          }
        clearMessageTimer = new javax.swing.Timer(1000, e -> resultLabel.setText(""));
        clearMessageTimer.setRepeats(false); 
        resultLabel.setForeground(Color.GREEN);
        clearMessageTimer.start();
        
    } else {
        playWrongSound();
        resultLabel.setText("ΑΠΑΝΤΗΣΕΣ ΛΑΘΟΣ");
        if (clearMessageTimer != null && clearMessageTimer.isRunning()) {
            clearMessageTimer.stop();
          }
        clearMessageTimer = new javax.swing.Timer(1000, e -> resultLabel.setText(""));
        clearMessageTimer.setRepeats(false); 
        resultLabel.setForeground(Color.RED);
        clearMessageTimer.start();
        
    }

  

    return;
    }



   // Question logic
    if(currentQ.answer == userAnswer && currentQuestion<20){
        correctQuestions++;
        playCorrectSound();
        resultLabel.setText("ΑΠΑΝΤΗΣΕΣ ΣΩΣΤΑ");
        if (clearMessageTimer != null && clearMessageTimer.isRunning()) {
        clearMessageTimer.stop();
        }
        clearMessageTimer = new javax.swing.Timer(1000, e -> resultLabel.setText(""));
        clearMessageTimer.setRepeats(false); 
        clearMessageTimer.start();

        resultLabel.setForeground(Color.GREEN);

    } else if(!(currentQ.answer == userAnswer) && currentQuestion<20){
        playWrongSound();
        resultLabel.setText("ΑΠΑΝΤΗΣΕΣ ΛΑΘΟΣ");
        if (clearMessageTimer != null && clearMessageTimer.isRunning()) {
        clearMessageTimer.stop();
        }
        clearMessageTimer = new javax.swing.Timer(1000, e -> resultLabel.setText(""));
        clearMessageTimer.setRepeats(false); 
        clearMessageTimer.start();
        resultLabel.setForeground(Color.RED);
    }
    
}

private static void showFinalScore(){
    endLabel.setText("");
}


//Display end of quiz Label
private static void endGame(int currentQuestion){
    endLabel.setText("ΤΕΛΟΣ QUIZ");
    
}





// Play "correct" sound effect if sound is enable 
private static void playCorrectSound(){
    if (sound==true){
    try{
        
        File soundFile = new File("resources/sounds/correct.sound.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);

        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();




    }catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
    }
    }
}




//Play "wrong" sound effect if sound is enable
private static void playWrongSound(){
    if(sound==true){
    try{
        
        File soundFile = new File("resources/sounds/wrong.sound.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);

        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();

    }catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
    }
    }
}







// Update the sound button icon depending on sound state
private static void changeIconSound(boolean sound,JButton soundBtn){
    if (sound==true){
        ImageIcon iconSound= new ImageIcon("resources/images/soundon.png");
        Image scaledImageSound = iconSound.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon scaledIconSound = new ImageIcon(scaledImageSound);
        soundBtn.setIcon(scaledIconSound);
    }else{
        ImageIcon iconSoundoff = new ImageIcon("resources/images/soundoff.png");
        Image scaledImageSound = iconSoundoff.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon scaledIconSound = new ImageIcon(scaledImageSound);
        soundBtn.setIcon(scaledIconSound);
    }


}





// ========== MAIN ==========

    public static void main(String[] args) throws Exception{


        // === Frame settings ===
        JFrame frame = new JFrame(); 
        frame.setSize(1000,1000); 
        frame.setTitle("Quiz");
        frame.getContentPane().setBackground(new Color(230, 240, 240)); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false); 
        
        ImageIcon image = new ImageIcon("resources/images/Computer.logo.png");
        frame.setIconImage(image.getImage());
        
        

        
        // === Title Label ===
        JLabel titleLabel = new JLabel(" QUIZ ΠΛΗΡΟΦΟΡΙΚΗ ",SwingConstants.CENTER); 
        titleLabel.setFont(new Font("Arial",Font.BOLD,30));
        titleLabel.setBounds(0, 0, 985, 80);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(240,240,240));
        Border border = BorderFactory.createLineBorder(Color.black,4);
        titleLabel.setBorder(border);
        frame.setLayout(null);
        frame.add(titleLabel);


        // === Result Label ===
        resultLabel = new JLabel(" ", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 24));
        resultLabel.setForeground(Color.BLUE); // Μπορείς να αλλάξεις χρώμα
        resultLabel.setBounds(285, 535, 400, 20); // Θέση κάτω από το ερώτημα
        frame.add(resultLabel);


       

        // === End Label ===
        endLabel = new JLabel(" ",SwingConstants.CENTER);
        endLabel.setFont(new Font("Arial", Font.BOLD, 24));
        endLabel.setBounds(290,500,390,20);
        endLabel.setForeground(Color.BLUE);
        frame.add(endLabel);





        successLabel = new JLabel(" ",SwingConstants.CENTER);
        successLabel.setFont(new Font("Arial", Font.BOLD, 24));
        successLabel.setBounds(300,550,390,100);
        successLabel.setForeground(Color.BLUE);
        frame.add(successLabel);





        // === Question Panel ===
        JPanel qPanel = new JPanel();
        qPanel.setBounds(50,150,880,200);
        qPanel.setBackground(new Color(230,230,250));
        qPanel.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        qPanel.setLayout(new BorderLayout());
        frame.add(qPanel);

        // === True Button ===
        ImageIcon icon = new ImageIcon("resources/images/swsto.png");
        Image scaledImage = icon.getImage().getScaledInstance(200, 80, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JButton trueBtn = new JButton(scaledIcon);
        trueBtn.setBounds(240,400,200,80);
        frame.add(trueBtn);

        // === False Button ===
        ImageIcon iconf = new ImageIcon("resources/images/lathos.png");
        Image scaledImagef = iconf.getImage().getScaledInstance(200, 80, Image.SCALE_SMOOTH);
        ImageIcon scaledIconf = new ImageIcon(scaledImagef);
        JButton falseBtn = new JButton(scaledIconf);
        falseBtn.setBounds(520,400,200,80);
        frame.add(falseBtn);



        // === Sound Button ===
        ImageIcon iconSound= new ImageIcon("resources/images/soundon.png");
        Image scaledImageSound = iconSound.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon scaledIconSound = new ImageIcon(scaledImageSound);
        JButton soundBtn = new JButton(scaledIconSound);
        soundBtn.setBounds(700,650,50,50);
        frame.add(soundBtn);        
         



        // === Restart Button ===
        ImageIcon iconr = new ImageIcon("resources/images/restart.png");
        Image scaledImager = iconr.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon scaledIconr = new ImageIcon(scaledImager);
        JButton restartButton = new JButton(scaledIconr);   
        restartButton.setBounds(630,650,50,50);
        frame.add(restartButton); 
        

        // === Question Counter ====
        final int totalQuestions = 20;
        questionCounterLabel = new JLabel("Ερώτηση " + currentQuestion + "/" + totalQuestions);
        questionCounterLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        questionCounterLabel.setBounds(440, 650, 200, 30); // x, y, width, height
        frame.add(questionCounterLabel);



        // === Load Questions ===
        LoadQuestions loader = new LoadQuestions();
        questions = loader.loadQuestions();

        

       
        // === Question Label ===
        qLabel = new JTextArea();
        qLabel.setFont(new Font("Arial", Font.PLAIN, 28));
        qLabel.setLineWrap(true);          // ενεργοποιεί το wrap
        qLabel.setWrapStyleWord(true);     // τυλίγει λέξεις σωστά, δεν τις κόβει
        qLabel.setEditable(false);         // να μην μπορεί να αλλάξει ο χρήστης
        qLabel.setOpaque(false);           // για να φαίνεται το background του panel
        qPanel.add(qLabel, BorderLayout.CENTER);



   
       // === Button Listeners === 


       // === True button listener ===
       trueBtn.addActionListener(e -> {
           
            if (currentQuestion<=20){
            checkAnswer(true);
            showRandomQuestion(currentQuestion);
            
            currentQuestion++;
            
            }
           
            
            if (currentQuestion==21){
                successLabel.setText("ΕΙΧΕΣ "+ correctQuestions+ " /20 ΣΩΣΤΕΣ");
                endGame(currentQuestion);
                qLabel.setText("END");
            }

            questionCounterLabel.setText("Ερώτηση " + Math.min(currentQuestion,totalQuestions) + "/" + totalQuestions);

            
            
            
        });


        // === False button listener ===
        falseBtn.addActionListener(e -> {
           
            if (currentQuestion<=20){
                checkAnswer(false);
                showRandomQuestion(currentQuestion);
                
                currentQuestion++;
                
            }
            
            if (currentQuestion==21){
                successLabel.setText("ΕΙΧΕΣ "+ correctQuestions+ " /20 ΣΩΣΤΕΣ");
                endGame(currentQuestion);
                qLabel.setText("END");
            }



            questionCounterLabel.setText("Ερώτηση " + Math.min(currentQuestion,totalQuestions) + "/" + totalQuestions);
            

        });


        // === Restart button listener ===
        restartButton.addActionListener(e ->{
            if(endMessageTimer != null && endMessageTimer.isRunning()){
                endMessageTimer.stop();
            }

            currentQuestion=1;
            questionCounterLabel.setText("Ερώτηση " + currentQuestion + "/" + totalQuestions);
            showRandomQuestion(currentQuestion);
            resultLabel.setText("");
            correctQuestions=0;
            endLabel.setText(" ");
            successLabel.setText("");

        });


        // === Sound button listener ===
        soundBtn.addActionListener(e ->{
            if (sound==true){
                sound=false;
                changeIconSound(sound,soundBtn);
                


            }else{
                sound=true;
                changeIconSound(sound,soundBtn);

            
            }


        });
       

        
        // === Start Quiz ===
        showRandomQuestion(currentQuestion);
        frame.setVisible(true);

    }

}

