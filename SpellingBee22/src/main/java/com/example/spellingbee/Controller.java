package com.example.spellingbee;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Controller {

    ArrayList<String> pangramKelimeAdaylari = new ArrayList<String>();
    ArrayList<String> farkliHarfler = new ArrayList<>();

    @FXML
    private Label puan;

    @FXML
    public Button Shuffle;

    @FXML
    public Button button1;

    @FXML
    public Button button2;

    @FXML
    public Button button3;

    @FXML
    public Button button4;

    @FXML
    public Button button5;

    @FXML
    public Button button6;

    @FXML
    public Button buttonS;

    @FXML
    public TextArea textArea;

    @FXML
    public TextField tftext;

    @FXML
    private TextField harf;

    @FXML
    private TextField ozelharf;

    @FXML
    private Label hataLabel;


    @FXML
    void tftextSet(ActionEvent event) {
        button1.setOnMouseClicked(mouseEvent -> {tftext.setText(tftext.getText()+button1.getText());});
        button2.setOnMouseClicked(mouseEvent -> {tftext.setText(tftext.getText()+button2.getText());});
        button3.setOnMouseClicked(mouseEvent -> {tftext.setText(tftext.getText()+button3.getText());});
        button4.setOnMouseClicked(mouseEvent -> {tftext.setText(tftext.getText()+button4.getText());});
        button5.setOnMouseClicked(mouseEvent -> {tftext.setText(tftext.getText()+button5.getText());});
        button6.setOnMouseClicked(mouseEvent -> {tftext.setText(tftext.getText()+button6.getText());});
        buttonS.setOnMouseClicked(mouseEvent -> {tftext.setText(tftext.getText()+buttonS.getText());});
    }
    /*void textAreaEdit(KeyEvent ke){
        if(ke.getCode()== KeyCode.ENTER){
            textAreaEkleme();
        }
    }*/

    public ArrayList<String> pangramKelime () throws IOException {

        if(farkliHarfler.size()>0){
            return farkliHarfler;
        }
        BufferedReader abc = new BufferedReader(new FileReader("/Users/alperkizilhan/Downloads/SpellingBee22/dictionary.txt"));


        HashMap<Object, Object> hmap = new HashMap<Object, Object>();
        ArrayList<String> hmapArrayList=new ArrayList<>();

        String line;
        while((line = abc.readLine()) != null) {
            pangramKelimeAdaylari.add(line.toUpperCase());
        }
        abc.close();

        String test="";
        for(int i=0;i<pangramKelimeAdaylari.size();i++) {
            test = pangramKelimeAdaylari.get(i);

            hmap = new HashMap<>();
            hmapArrayList = new ArrayList<>();

            for (int a = 0; a < test.length(); a++) {
                if (!hmap.containsKey(test.charAt(a))) {
                    hmap.put(test.charAt(a), 1);

                } else {
                    int count = (int) hmap.get(test.charAt(a));
                    hmap.put(test.charAt(a), count + 1);
                    hmapArrayList.add(String.valueOf(hmap));
                }
            }

                Object[] randomharfler = hmap.keySet().toArray();
                if (randomharfler.length == 7) {
                    farkliHarfler.add(hmap.keySet().toString());
                /*System.out.println(hmap.keySet());
                System.out.println(hmap.values());*/
                }

        }



        return farkliHarfler;

    }

    @FXML
    void btnRandomClicked(ActionEvent event) throws IOException {
        ArrayList<String> farkliHarfler=pangramKelime();
        int random =(int) (Math.random()*(farkliHarfler.size()));
        String farkliHarflerString ;

        farkliHarflerString = farkliHarfler.get(random).replaceAll(" [^a-zA-ZğüşöçıĞÜİŞÖÇ]*", "");
        farkliHarflerString=farkliHarflerString.replaceAll("[i]","İ");
        farkliHarflerString=farkliHarflerString.replaceAll("[,]","");

        System.out.println(farkliHarflerString);

        int score=0;
        puan.setText(String.valueOf(score));
        textArea.clear();

        List<String> kelimeler=kelimeKarsilastirici(pangramKelimeAdaylari,farkliHarflerString);
        System.out.println(kelimeler);

        if (!(kelimeler.size() > 20 )) {
            button1.setText("");
            button2.setText("");
            button3.setText("");
            button4.setText("");
            button5.setText("");
            button6.setText("");
            buttonS.setText("");
            hataLabel.setTextFill(Color.RED);
            hataLabel.setText("OYUN OYNANAMAZ, TEKRAR DENEYİNİZ");
        }else{
            button1.setText(String.valueOf(farkliHarflerString.charAt(1)).toUpperCase(Locale.ROOT));
            button2.setText(String.valueOf(farkliHarflerString.charAt(2)).toUpperCase(Locale.ROOT));
            button3.setText(String.valueOf(farkliHarflerString.charAt(3)).toUpperCase(Locale.ROOT));
            button4.setText(String.valueOf(farkliHarflerString.charAt(4)).toUpperCase(Locale.ROOT));
            button5.setText(String.valueOf(farkliHarflerString.charAt(5)).toUpperCase(Locale.ROOT));
            button6.setText(String.valueOf(farkliHarflerString.charAt(6)).toUpperCase(Locale.ROOT));
            buttonS.setText(String.valueOf(farkliHarflerString.charAt(7)).toUpperCase(Locale.ROOT));
            hataLabel.setTextFill(Color.GREEN);
            hataLabel.setText("OYUN OYNANABİLİR");
        }

    }

    String regEx(String a ){
        a.replaceAll("[^a-zA-ZğüşöçıĞÜİŞÖÇ]*","");
        a.replaceAll("[+,*/_!?.0-9,-.[,]]","");
        return a;
    }

    @FXML
    void btnAddClicked(ActionEvent event) throws IOException {
        ArrayList<String> farkliHarfler=pangramKelime();
        String farkliHarflerString ;
        int score= Integer.parseInt(puan.getText());
        String harfler = harf.getText().toUpperCase();
        harfler.trim();
        String kullaniciHarf = regEx(harfler);

        System.out.println(kullaniciHarf);
        String ozel = ozelharf.getText().toUpperCase();
        ozel.trim();
        String kullaniciOzelHarf = regEx(ozel);

        System.out.println(kullaniciOzelHarf); //[aıryklş] 1
//[a,b,r,s,u,j,z]
        boolean karsilastirmaSonucu=false;
        for(int i=0;i<farkliHarfler.size();i++) {
            farkliHarflerString = farkliHarfler.get(i).replaceAll(" [^a-zA-ZğüşöçıĞÜİŞÖÇ]*", "");
            // farkliHarflerString=farkliHarflerString.replaceAll("[i]","İ");
            farkliHarflerString = farkliHarflerString.replaceAll("[+,*/_!?.0-9,-.[,]]", "");
            if (kullaniciHarf.length() == 6 && kullaniciOzelHarf.length() == 1) { // bu kısmı dışarı taşıyabiliriz. ayrı metotlara böl.
                if (!kullaniciHarf.contains(kullaniciOzelHarf)) {
                    int x = 0;
                    for (int a = 0; a < 6; a++) {
                        for (int j = 1; j < 6; j++) {

                            if ((kullaniciHarf.charAt(a) == kullaniciHarf.charAt(j)) && a != j) {
                                x++;
                            }
                        }
                    }
                    if (x == 0) {
                        String yeniFarkliHarfler=kullaniciHarf+kullaniciOzelHarf;
                        karsilastirmaSonucu= kelimeleriKarsilastir(yeniFarkliHarfler.toUpperCase(), farkliHarflerString.toUpperCase());

                       // if (farkliHarflerString.contains(harf.getText()) && farkliHarflerString.contains(ozelharf.getText())) {
                        if (karsilastirmaSonucu){
                            hataLabel.setText(" ");

                            /*buttonS.setText(ozelharf.getText().toUpperCase(Locale.ROOT));

                            button1.setText(String.valueOf(kullaniciHarf.charAt(0)));
                            button2.setText(String.valueOf(kullaniciHarf.charAt(1)));
                            button3.setText(String.valueOf(kullaniciHarf.charAt(2)));
                            button4.setText(String.valueOf(kullaniciHarf.charAt(3)));
                            button5.setText(String.valueOf(kullaniciHarf.charAt(4)));
                            button6.setText(String.valueOf(kullaniciHarf.charAt(5)));

                            hataLabel.setTextFill(Color.GREEN);
                            hataLabel.setText("HARFLER BAŞARIYLA EKLENDİ");*/
                            List<String> kelimeler=kelimeKarsilastirici(pangramKelimeAdaylari,farkliHarflerString);
                            System.out.println(kelimeler);

                            if (!(kelimeler.size() > 20 )) {
                                button1.setText("");
                                button2.setText("");
                                button3.setText("");
                                button4.setText("");
                                button5.setText("");
                                button6.setText("");
                                buttonS.setText("");
                                hataLabel.setTextFill(Color.RED);
                                hataLabel.setText("OYUN OYNANAMAZ, TEKRAR DENEYİNİZ");
                                karsilastirmaSonucu=false;
                            }else{
                                button1.setText(String.valueOf(farkliHarflerString.charAt(1)).toUpperCase(Locale.ROOT));
                                button2.setText(String.valueOf(farkliHarflerString.charAt(2)).toUpperCase(Locale.ROOT));
                                button3.setText(String.valueOf(farkliHarflerString.charAt(3)).toUpperCase(Locale.ROOT));
                                button4.setText(String.valueOf(farkliHarflerString.charAt(4)).toUpperCase(Locale.ROOT));
                                button5.setText(String.valueOf(farkliHarflerString.charAt(5)).toUpperCase(Locale.ROOT));
                                button6.setText(String.valueOf(farkliHarflerString.charAt(6)).toUpperCase(Locale.ROOT));
                                buttonS.setText(String.valueOf(farkliHarflerString.charAt(7)).toUpperCase(Locale.ROOT));
                                hataLabel.setTextFill(Color.GREEN);
                                hataLabel.setText("OYUN OYNANABİLİR");
                            }
                            score = 0;
                            puan.setText(String.valueOf(score));
                            textArea.clear();
                        } else hataLabel.setText("Pangram kelime içermiyor");
                    } else hataLabel.setText("Ayni harfleri iceriyor");
                } else hataLabel.setText("Ozel harf ve harfler ayni olmamalı");
            } else hataLabel.setText("GİRİLEN HARF SAYILARINA DİKKAT EDİNİZ !!");
            if(karsilastirmaSonucu){
                break;
            }
        }
    }

    public List<String> kelimeKarsilastirici(List<String> kelimeler, String farkliHarfler){
        List<String> uygunKelimeler = kelimeler.
                stream().filter(t->t.length() > 4).
                filter((String kelime) -> kelimeleriBul(kelime.toUpperCase(),farkliHarfler.toUpperCase())).
                collect(Collectors.toList());
        return uygunKelimeler;
    }
    private boolean kelimeleriBul(String yeniFarkliHarfler, String farkliHarflerString) {
        String enYeniFarkliHarfler=yeniFarkliHarfler;
        farkliHarflerString=farkliHarflerString.replace("[","");
        farkliHarflerString=farkliHarflerString.replace("]","");
        for(int i=0;i<enYeniFarkliHarfler.length();i++){
            boolean flag=true;
            for(int j=0;j<farkliHarflerString.length();j++){
                if(farkliHarflerString.charAt(j)==enYeniFarkliHarfler.charAt(i)){
                    flag=false;
                    yeniFarkliHarfler=yeniFarkliHarfler.replace(String.valueOf(enYeniFarkliHarfler.charAt(i)),"");
                    break;
                }
            }
            if(flag){
                return false;
            }
        }
        if (yeniFarkliHarfler.length() > 0){
            return false;
        }
        return true;
    }

    public String removeChar(String str, Integer n) {
        String front = str.substring(0, n);
        String back = str.substring(n+1, str.length());
        return front + back;
    }

    private boolean kelimeleriKarsilastir(String yeniFarkliHarfler1, String farkliHarflerString1) {
        farkliHarflerString1=farkliHarflerString1.replace("[","");
        farkliHarflerString1=farkliHarflerString1.replace("]","");
        for(int i=0;i<farkliHarflerString1.length();i++){
            boolean flag=true;
            for(int j=0;j<yeniFarkliHarfler1.length();j++){
                if(farkliHarflerString1.charAt(i)==yeniFarkliHarfler1.charAt(j)){
                    flag=false;
                    yeniFarkliHarfler1=removeChar(yeniFarkliHarfler1,j);
                    break;
                }
            }
            if(flag){
                return false;
            }
        }
        return true;
    }

    @FXML
    protected void btnDeleteClicked(ActionEvent event) {
        if(tftext.getLength()!=0){
            tftext.deleteText(tftext.getLength()-1,tftext.getLength());
        }
    }

    @FXML
    void btnEnterClicked(ActionEvent event) {
        textAreaEkleme();
    }

    public void puanKontrol(int score){
        if(score>400){
            hataLabel.setText("OYUN BAŞARIYLA TAMAMLANDI");
            textArea.clear();
        }

    }

    void textAreaEkleme(){
        Application app = new Application();
        ArrayList<String> word2 = new ArrayList<>();
        int score = Integer.parseInt(puan.getText());
        int kUzunluk = tftext.getLength() - 3;
        word2 = pangramKelimeAdaylari;
        //CONTAİNS YERİNE STREAM KULANABİLİRİZ.
        if (word2.contains(tftext.getText()) && tftext.getText().contains(buttonS.getText()) && tftext.getLength() >= 4 && !(textArea.getText().contains(tftext.getText()))) {
            textArea.appendText(tftext.getText().toUpperCase());
            textArea.appendText("\n");
            tftext.clear();
            hataLabel.setTextFill(Color.GREEN);
            hataLabel.setText("Kelime başarıyla eklendi !!");
            if (kUzunluk > 0) {
                puanKontrol(score+kUzunluk);
                puan.setText(String.valueOf(score + kUzunluk));
                if(textArea.getText().contains(button1.getText()) && textArea.getText().contains(button2.getText()) && textArea.getText().contains(button3.getText())
                        && textArea.getText().contains(button5.getText())&& textArea.getText().contains(button4.getText())&& textArea.getText().contains(button6.getText())
                        && textArea.getText().contains(buttonS.getText())){
                    puan.setText(String.valueOf(score+kUzunluk+7));
                }

            } else hataLabel.setText("KISA KELİME");
        } else hataLabel.setText("Kelimeye Dikkat Ediniz ! ");

    }

    @FXML
    protected void btnShuffleClicked(ActionEvent event) {
        ArrayList<String> buttonRandom=new ArrayList<>();
        buttonRandom.add(button1.getText());
        buttonRandom.add(button2.getText());
        buttonRandom.add(button3.getText());
        buttonRandom.add(button4.getText());
        buttonRandom.add(button5.getText());
        buttonRandom.add(button6.getText());
        Collections.shuffle(buttonRandom);
        button1.setText(buttonRandom.get(0));
        button2.setText(buttonRandom.get(1));
        button3.setText(buttonRandom.get(2));
        button4.setText(buttonRandom.get(3));
        button5.setText(buttonRandom.get(4));
        button6.setText(buttonRandom.get(5));
    }


}
