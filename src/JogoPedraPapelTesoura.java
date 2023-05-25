// Abdias Saint Louis
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class JogoPedraPapelTesoura {

    public static void main(String[] args) {
        try {
            int porta = 2703;
            ServerSocket serverSocket = new ServerSocket(porta);
            System.out.println("Servidor iniciado na porta " + porta);

            while (true) {
                Socket cliente1 = serverSocket.accept();
                System.out.println("Cliente 1 conectado: " + cliente1.getInetAddress().getHostAddress());
                PrintWriter outCliente1 = new PrintWriter(cliente1.getOutputStream(), true);
                BufferedReader inCliente1 = new BufferedReader(new InputStreamReader(cliente1.getInputStream()));

                outCliente1.println("Bem-vindo ao Jogo Pedra-Papel-Tesoura, " + "Abdias" + "!");
                outCliente1.println("Por favor, aguarde outro jogador se conectar.");

                Socket cliente2 = serverSocket.accept();
                System.out.println("Cliente 2 conectado: " + cliente2.getInetAddress().getHostAddress());
                PrintWriter outCliente2 = new PrintWriter(cliente2.getOutputStream(), true);
                BufferedReader inCliente2 = new BufferedReader(new InputStreamReader(cliente2.getInputStream()));

                outCliente1.println("Um adversário conectou-se ao jogo!");
                outCliente2.println("Bem-vindo ao Jogo Pedra-Papel-Tesoura, " + "Alan" + "!");

                int vitoriasJogador1 = 0;
                int vitoriasJogador2 = 0;

                while (true) {
                    // Jogador 1 escolhe sua jogada
                    outCliente1.println("Sua vez, " + "Abdias" + "! Escolha sua jogada (0 = Pedra, 1 = Papel, 2 = Tesoura):");
                    int jogadaJogador1 = Integer.parseInt(inCliente1.readLine());

                    // Jogador 2 escolhe sua jogada
                    outCliente2.println("Sua vez, " + "Alan" + "! Escolha sua jogada (0 = Pedra, 1 = Papel, 2 = Tesoura):");
                    int jogadaJogador2 = Integer.parseInt(inCliente2.readLine());

                    // Determina o vencedor da rodada
                    int resultado = determinaResultado(jogadaJogador1, jogadaJogador2);

                    // Informa o resultado da rodada aos jogadores
                    if (resultado == 0) {
                        outCliente1.println("Empate! Ambos jogaram " + jogadaToString(jogadaJogador1) + ".");
                        outCliente2.println("Empate! Ambos jogaram " + jogadaToString(jogadaJogador2) + ".");
                    } else if (resultado == 1) {
                        outCliente1.println("Parabéns, " + "Abdias" + "! Você venceu esta rodada jogando " + jogadaToString(jogadaJogador1) + ".");
                        outCliente2.println("Infelizmente, " + "Alan" + ", você perdeu esta rodada. " + "Abdias" + " jogou " + jogadaToString(jogadaJogador1) + ".");
                        vitoriasJogador1++;
                    } else if (resultado == 2) {
                        outCliente1.println("Que pena, " + "Abdias" + "! Você perdeu esta rodada. " + "Alan" + " jogou " + jogadaToString(jogadaJogador2) + ".");
                        outCliente2.println("Parabéns, " + "Alan" + "! Você venceu esta rodada jogando " + jogadaToString(jogadaJogador2) + ".");
                        vitoriasJogador2++;
                    }

                    // Verifica se algum jogador atingiu o número de vitórias necessário para vencer o jogo
                    if (vitoriasJogador1 == 3) {
                        outCliente1.println("Parabéns, " + "Abdias" + "! Você venceu o jogo!");
                        outCliente2.println("Infelizmente, " + "Alan" + ", você perdeu o jogo. " + "Abdias" + " venceu por " + vitoriasJogador1 + " a " + vitoriasJogador2 + ".");
                        break;
                    } else if (vitoriasJogador2 == 3) {
                        outCliente1.println("Que pena, " + "Abdias" + "! Você perdeu o jogo. " + "Alan" + " venceu por " + vitoriasJogador2 + " a " + vitoriasJogador1 + ".");
                        outCliente2.println("Parabéns, " + "Alan" + "! Você venceu o jogo!");
                        break;
                    }
                }

                // Fecha as conexões com os jogadores
                cliente1.close();
                cliente2.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int determinaResultado(int jogadaJogador1, int jogadaJogador2) {
        if (jogadaJogador1 == jogadaJogador2) {
            return 0; // empate
        } else if (jogadaJogador1 == 0 && jogadaJogador2 == 2) {
            return 1; // jogador 1 ganha (pedra vs. tesoura)
        } else if (jogadaJogador1 == 1 && jogadaJogador2 == 0) {
            return 1; // jogador 1 ganha (papel vs. pedra)
        } else if (jogadaJogador1 == 2 && jogadaJogador2 == 1) {
            return 1; // jogador 1 ganha (tesoura vs. papel)
        } else {
            return 2; // jogador 2 ganha
        }
    }

    private static String jogadaToString(int jogada) {
        switch (jogada) {
            case 0:
                return "Pedra";
            case 1:
                return "Papel";
            case 2:
                return "Tesoura";
            default:
                return "";
        }
    }
}