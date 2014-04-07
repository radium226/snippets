package singleton;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static int PORT = 6666;

    public static ServerSocket SERVER_SOCKET = null; 

    public static boolean hasMainAlreadyBeenInvoked() {
        if (SocketUtil.isPortAvailable(PORT)) {
            Log.inform("Le port " + PORT + " est disponible, donc c'est la première instance. ");
            return true;
        } else {
            Log.inform("Le port " + PORT + " n'est pas disponible, donc ce n'est pas la première instance. ");
            return false;
        }
    }

    public static void main(String[] arguments) {
        if (!hasMainAlreadyBeenInvoked()) {
            Log.inform("Une autre instance est en cours. On lui envoie les arguments. ");
            MainInvokationEvent newMainInvokationEvent = new MainInvokationEvent(arguments);
            sendMainInvokationEvent(newMainInvokationEvent);
        
        } else {
            Log.inform("Ceci est la première instance ! ");
            final MainEventListener mainEventListener = new MainEventListener() {

                public void handleEvent(MainEvent mainEvent) {
                    if (mainEvent.getType().equals(MainEvent.Type.INVOKATION)) {
                        final MainInvokationEvent mainInvokationEvent = (MainInvokationEvent) mainEvent;
                        final String[] arguments = mainInvokationEvent.getArguments();
                        mainInvoked(arguments);
                    }
                }

            };
            Threads.start(new Runnable() {
                public void run() {
                    receiveMainInvokationEvent(mainEventListener);
                }
            });
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

                public void run() {
                    Log.inform("On arrête le serveur. ");
                    closeQuietly(SERVER_SOCKET);
                    Threads.interrupt();
                }

            }));
            sendMainInvokationEvent(new MainInvokationEvent(arguments));
        }
    }

    public static void mainInvoked(String[] arguments) {
        Log.inform("Main invoké !");
        System.out.println(" --> " + arguments[0]);
        try {
            Thread.sleep(20 * 1000);
        } catch (InterruptedException ex) {
            Log.inform("Bon... On arrête les frais.");
        }
    }

    public static void sendMainInvokationEvent(MainInvokationEvent mainInvokationEvent) {
        Socket socket = null;
        OutputStream socketOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            socket = new Socket(InetAddress.getByAddress(new byte[]{127, 0, 0, 1}), PORT);
            socketOutputStream = socket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(socketOutputStream);
            objectOutputStream.writeObject(mainInvokationEvent);
            objectOutputStream.flush();
            socket.close();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            closeQuietly(new OutputStream[] {
                    objectOutputStream,
                    socketOutputStream
                });
            closeQuietly(socket);
        }
    }

    public static void receiveMainInvokationEvent(final MainEventListener mainInvokationEventListener) {
        try {
            SERVER_SOCKET = new ServerSocket(PORT);
            
            while (true) {
                try {
                    final Socket clientSocket = SERVER_SOCKET.accept();
                    Log.inform("Nouvelle connexion !");
                    Threads.start(new Runnable() {

                        public void run() {
                            InputStream clientSocketInputStream = null;
                            ObjectInputStream mainInvokationEventInputStream = null;
                            try {
                                clientSocketInputStream = clientSocket.getInputStream();
                                mainInvokationEventInputStream = new ObjectInputStream(clientSocketInputStream);
                                MainInvokationEvent mainInvokationEvent = (MainInvokationEvent) mainInvokationEventInputStream.readObject();
                                mainInvokationEventListener.handleEvent(mainInvokationEvent);
                            } catch (Exception ex) {
                                ex.printStackTrace(System.err);
                                throw new RuntimeException(ex);
                            } finally {
                                closeQuietly(new InputStream[] {
                                        mainInvokationEventInputStream,
                                        clientSocketInputStream
                                    });
                            }
                        }

                    });
                } catch (IOException e) {
                    Log.inform("Serveur arrêté ?");
                    return;
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void closeQuietly(InputStream... inputStreams) {
        for (InputStream inputStream : inputStreams) {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace(System.out);
            }
        }
    }

    public static void closeQuietly(OutputStream... outputStreams) {
        for (OutputStream outputStream : outputStreams) {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace(System.out);
            }
        }
    }

    public static void closeQuietly(Socket... sockets) {
        for (Socket socket : sockets) {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace(System.out);
            }
        }
    }

    public static void closeQuietly(ServerSocket... serverSockets) {
        for (ServerSocket serverSocket : serverSockets) {
            try {
                if (serverSocket != null) {
                    System.out.println("Huhuhuhu");
                    serverSocket.close();
                    System.out.println("Hohohoho");
                }
            } catch (IOException exception) {
                System.out.println("Ha bah merde...");
                exception.printStackTrace(System.out);
            }
        }
    }

}
