public class Main extends Application {

    private ClienteJogoPedraPapelTesoura cliente;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Inicializa o cliente
        this.cliente = new ClienteJogoPedraPapelTesoura();
        this.cliente.conectar();

        // Carrega o arquivo FXML para a interface gráfica
        FXMLLoader loader = new FXMLLoader(getClass().getResource("interface.fxml"));

        // Cria um novo controlador de interface gráfica
        InterfaceController controller = new InterfaceController(cliente);

        // Define o controlador para o arquivo FXML
        loader.setController(controller);

        // Carrega a cena do arquivo FXML
        Parent root = loader.load();

        // Cria a janela principal
        primaryStage.setTitle("Jogo Pedra-Papel-Tesoura");
        primaryStage.setScene(new Scene(root, 600, 400));

        // Exibe a janela principal
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        // Desconecta o cliente ao fechar a janela
        this.cliente.desconectar();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
