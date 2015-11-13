package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {
    public AnchorPane mainPane;
    public MenuItem miSortir;
    public MenuItem miCopiar;
    public MenuItem miTallar;
    public MenuItem miEnganxar;
    public MenuItem miDesfer;
    public MenuItem miRefer;
    public MenuItem miHelp;
    public MenuItem cMcopy;
    public MenuItem cMtallar;
    public Button btCopy;
    public CheckMenuItem ChMsystem;
    public TextArea text;
    public TextField fontTamany;
    public CheckMenuItem lastColor;
    public CheckMenuItem lastFont;
    private Stage mainStage=new Main().getStage();
    private ArrayList<String> fontEstils=new ArrayList<>();
    private String postura="REGULAR";
    private String pes="NORMAL";

    /**
     *Marca la el estil de la font per defecte al CheckMenuItem i mostra el tamany de la font per defecte
     */
    public void initialize(){
        btCopy.setGraphic(new ImageView("copy.png"));
        fontTamany.setText(String.valueOf(text.getFont().getSize()));
    }

    /**
     * Tancar la aplicació
     * @param actionEvent
     */
    public void sortir(ActionEvent actionEvent) {Platform.exit();}

    /**
     * Copiar el text seleccionat en un buffer. Equivalent a Ctrl+c
     * @param actionEvent
     */
    public void copiar(ActionEvent actionEvent) {text.copy();}

    /**
     * Borra el text seleccionat pero abans el copia en un buffer. Equivalent a Ctrl+x
     * @param actionEvent
     */
    public void tallar(ActionEvent actionEvent) {text.cut();}

    /**
     * Enganza el text abans copiat al buffer. Quivalent a Ctrl+v
     * @param actionEvent
     */
    public void enganxar(ActionEvent actionEvent) {text.paste();}

    /**
     * Desfà l'acció anterior.
     * @param actionEvent
     */
    public void desfer(ActionEvent actionEvent) {text.undo();}

    /**
     * Refà l'acció de desfer aterior.
     * @param actionEvent
     */
    public void refer(ActionEvent actionEvent){text.redo();}

    /**
     * Canvia la font del text mostrat al text àrea a la font escollida.
     * @param actionEvent
     */
    public void setFont(ActionEvent actionEvent) {
        if(lastFont!=null&&lastFont.isSelected())lastFont.setSelected(false);

        CheckMenuItem item=(CheckMenuItem) actionEvent.getSource();
        item.setSelected(true);
        text.setFont(Font.font(item.getText(), text.getFont().getSize()));
        lastFont=item;
     }


    /**
     * Canvia el tamany de la font al número seleccionat.
     * @param actionEvent
     */
    public void setFontSize(ActionEvent actionEvent) {
        double fontSize;
        try {
            fontSize = Double.parseDouble(fontTamany.getText());
        }catch(NumberFormatException e){
            fontSize=text.getFont().getSize();
            fontTamany.setText(String.valueOf(fontSize));
        }
        text.setFont(new Font(fontSize));
    }

    /**
     * Mostra un Alert.
     * @param actionEvent
     */
    public void getInfo(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Editor ");
        alert.setHeaderText("Editor de texto del Chino de los Chinos");
        alert.setContentText("Tiene sus opciones y hasta parece decente.");
        alert.showAndWait();
    }

    /**
     * Deshabilita si no hi ha text seleccionat les opcions de Copiar i tallar del menú Editar.
     * @param event
     */
    public void disableMenuEditItems(Event event) {
        if (text.getSelectedText().equals("")) {
            miCopiar.setDisable(true);
            miTallar.setDisable(true);
        } else {
            miCopiar.setDisable(false);
            miTallar.setDisable(false);
        }
    }
    /**
     * Deshabilita si no hi ha text seleccionat les opcions de Copiar i tallar del menú contextual del TextArea.
     * @param event
     */
    public void disableContextItems(Event event) {
        if (text.getSelectedText().equals("")) {
            cMcopy.setDisable(true);
            cMtallar.setDisable(true);
        } else {
            cMcopy.setDisable(false);
            cMtallar.setDisable(false);
        }
    }

    /**
     * Permet establir l'estil en que es mostra la font al TexArea ((Negreta i/o Cursiva) o Normal)
     * @param actionEvent
     * @see #fontEstils(boolean, String)
     */
    public void setFontStyle(ActionEvent actionEvent) {

        CheckMenuItem item=(CheckMenuItem) actionEvent.getSource();
        if(fontEstils.contains(item.getText())){
            fontEstils(true, item.getText());
            fontEstils.remove(item.getText());
        }else{
            fontEstils(false, item.getText());
            fontEstils.add(item.getText());
        }
    }

    /**
     * Funció auxiliar cridada per setFontStyle per activa o desactival l'estil corresponent al CheckMenuItem clicat.
     * @param isActive Booleà que indica si l'estil está marca al CheckMenuItem.
     * @param estil Tipus d'estil (Negreta o Cursiva)
     */
    private void fontEstils(boolean isActive, String estil){
        switch (estil){
            case "Negreta":
                if(isActive) {
                    pes = "NORMAL";
                    text.setFont(Font.font(text.getFont().getStyle(), FontWeight.NORMAL, FontPosture.valueOf(postura)
                            , text.getFont().getSize()));
                }else {
                    pes = "BOLD";
                    text.setFont(Font.font(text.getFont().getStyle(), FontWeight.BOLD, FontPosture.valueOf(postura)
                            , text.getFont().getSize()));
                }
                break;
            case "Cursiva":
                String a=text.getText();
                if(isActive) {
                    postura = "REGULAR";
                    text.setFont(Font.font(text.getFont().getStyle(), FontWeight.valueOf(pes), FontPosture.REGULAR,
                            text.getFont().getSize()));
                }else {
                    postura = "ITALIC";
                    text.setFont(Font.font(text.getFont().getStyle(), FontWeight.valueOf(pes), FontPosture.ITALIC
                            , text.getFont().getSize()));
                }
                break;
        }
    }

    /**
     * Canvia el color de la font.
     * @param actionEvent
     */
    public void setFontColor(ActionEvent actionEvent) {
        if(lastColor!=null&&lastColor.isSelected())lastColor.setSelected(false);
        CheckMenuItem item=(CheckMenuItem) actionEvent.getSource();
        item.setSelected(true);

        text.setStyle("-fx-text-fill: #" + item.getId().substring(1));
        lastColor=item;
    }

    /**
     * Obre un fitxer per carregar un fitxer .txt
     * @param actionEvent
     * @see #openFile(File)
     */
    public void obrirFitexr(ActionEvent actionEvent) {
        MenuItem item=(MenuItem)actionEvent.getSource();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Arxius suportats", "*.txt"),
                new FileChooser.ExtensionFilter("Tots els arxius", "*.*"));
        File arxiu=fileChooser.showOpenDialog(mainStage);
        if (arxiu != null) {
            openFile(arxiu);
        }
        //Stage stageTheLabelBelongs = (Stage) label.getScene().getWindow();
    }

    /**
     * Mètode auxiliar cridat per obrirFitxer que s'encarrega de carregar el contingut el textArea al fitxer creat,
     * @param arxiu Fitxer vuit passat per la funció obrirFitxer.
     * @see #setEditorTitle(String)
     */
    private void openFile(File arxiu) {
        try{

            BufferedReader carregador=new BufferedReader(new FileReader(arxiu));
            String copiador;
            StringBuilder creadorText= new StringBuilder();
            setEditorTitle(arxiu.getName());
            while((copiador=carregador.readLine())!=null){
                creadorText.append(copiador+"\n");
            }
            text.setText(creadorText.toString());

        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    /**
     * Guarda el contingut del TextArea en el fitxer indicat.
     * @param actionEvent
     * @see #saveFile(File, String)
     */
    public void guardarFitxer(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        File arxiu = fileChooser.showSaveDialog(mainStage);
        StringBuilder guardador=new StringBuilder(text.getText());
        if (arxiu != null) {
                saveFile(arxiu, guardador.toString());
        }

    }

    /**
     * Mètode auxiliar cridat per guardarFitxer que carrega el contingut del textArea en el fitxer indicat per paràmetre
     * @param arxiu
     * @param contingut
     */
    public void saveFile(File arxiu, String contingut){
        try{
            BufferedWriter escritor = new BufferedWriter(new FileWriter(arxiu));
            escritor.write(contingut);
            escritor.close();
        }catch (IOException ex){

        }

    }

    /**
     * Mètode auxiliar cridat per openFile que mostra el nom del fitxer obrit en la capçelera del editor (titol del Stage)
     * @param arxiu
     */
    public void setEditorTitle(String arxiu){
        Main.getStage().setTitle("SIMPLE EDITOR       current file: " + arxiu);
    }

}
