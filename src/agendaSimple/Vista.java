package agendaSimple;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.UIManager;

public class Vista extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JLabel lblNombreArchivo;
	private JTextArea textAreaNota;
	private JLabel lblNota;
	private JButton btnCargarArchivo;
	private JButton btnSalir;
	private JButton btnGuardar;
	private JLabel lblResultado;
	private JTextField txtRutaFichero;
	private JTextField txtFecha;
	private JLabel lblFormatoFecha;
	private JLabel lblTituloApp;

	/**
	 * <strong>Constructor de la clase Vista</strong> Este costructor genera todo el
	 * aspecto visual
	 * 
	 */
	public Vista() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Vista.class.getResource("/recursos/Icon32x32.png")));
		setResizable(false);
		setTitle("Agenda Simple");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 606, 442);

		panel = new JPanel();
		panel.setForeground(new Color(255, 255, 255));
		panel.setLayout(null);
		panel.setBackground(new Color(61, 68, 97));
		setContentPane(panel);

		lblNombreArchivo = new JLabel("Nombre del archivo:");
		lblNombreArchivo.setBackground(new Color(107, 131, 140));
		lblNombreArchivo.setForeground(new Color(255, 255, 255));
		lblNombreArchivo.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNombreArchivo.setBounds(34, 61, 260, 20);
		panel.add(lblNombreArchivo);

		lblNota = new JLabel("Nota:");
		lblNota.setForeground(new Color(255, 255, 255));
		lblNota.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNota.setBounds(34, 129, 260, 20);
		panel.add(lblNota);

		JScrollPane scrollPaneNota = new JScrollPane();
		scrollPaneNota.setBounds(34, 152, 525, 170);
		panel.add(scrollPaneNota);

		textAreaNota = new JTextArea();
		textAreaNota.setLineWrap(true);
		scrollPaneNota.setViewportView(textAreaNota);

		btnCargarArchivo = new JButton("Cargar");
		btnCargarArchivo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnCargarArchivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				seleccionaFichero();
			}
		});
		btnCargarArchivo.setForeground(Color.BLACK);
		btnCargarArchivo.setBounds(357, 333, 96, 34);
		panel.add(btnCargarArchivo);

		btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!(textAreaNota.getText().equals("") || txtFecha.getText().equals("")
						|| txtRutaFichero.getText().equals(""))) {
					crearFichero();
					lblResultado.setText("");
					textAreaNota.setText("");
					txtRutaFichero.setText("");
					txtFecha.setText("");
				} else {
					lblResultado.setText("Rellena todos los campos.");
				}
			}
		});
		btnGuardar.setForeground(Color.BLACK);
		btnGuardar.setBounds(463, 333, 96, 34);
		panel.add(btnGuardar);

		btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnSalir.setForeground(Color.BLACK);
		btnSalir.setBounds(34, 333, 96, 34);
		panel.add(btnSalir);

		lblResultado = new JLabel("");
		lblResultado.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblResultado.setHorizontalAlignment(SwingConstants.RIGHT);
		lblResultado.setBounds(107, 129, 448, 20);
		lblResultado.setForeground(new Color(255, 0, 0));
		panel.add(lblResultado);

		txtRutaFichero = new JTextField();
		txtRutaFichero.setColumns(10);
		txtRutaFichero.setBounds(299, 61, 260, 20);
		panel.add(txtRutaFichero);

		txtFecha = new JTextField();
		txtFecha.setColumns(10);
		txtFecha.setBounds(299, 92, 260, 20);
		panel.add(txtFecha);

		JLabel lblFechaNota = new JLabel("Fecha de la nota:");
		lblFechaNota.setForeground(Color.WHITE);
		lblFechaNota.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblFechaNota.setBackground(new Color(107, 131, 140));
		lblFechaNota.setBounds(34, 92, 260, 20);
		panel.add(lblFechaNota);

		lblFormatoFecha = new JLabel("Formato recomendado: DD/MM/AAAA");
		lblFormatoFecha.setForeground(Color.WHITE);
		lblFormatoFecha.setBounds(301, 115, 260, 14);
		panel.add(lblFormatoFecha);

		lblTituloApp = new JLabel("Agenda Simple");
		lblTituloApp.setHorizontalAlignment(SwingConstants.CENTER);
		lblTituloApp.setForeground(Color.WHITE);
		lblTituloApp.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 24));
		lblTituloApp.setBackground(new Color(107, 131, 140));
		lblTituloApp.setBounds(34, 15, 525, 35);
		panel.add(lblTituloApp);
	}

	/**
	 * Metodo que abre un gestor de archvios filtrando por ".txt" para que el
	 * usurario seleccione el fichero que desee cargar. Llama al metodo
	 * <code> mostrarFichero(File) </code>
	 * 
	 * @return Void
	 */
	private void seleccionaFichero() {
		File rutaProyecto = new File(System.getProperty("user.dir"));
		JFileChooser fc = new JFileChooser(rutaProyecto);
		FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.txt", "txt");
		fc.setFileFilter(filtro);
		int seleccion = fc.showOpenDialog(panel);
		if (seleccion == JFileChooser.APPROVE_OPTION) {
			File fichero = fc.getSelectedFile();
			txtRutaFichero.setText(fichero.getName().substring(0, fichero.getName().length() - 4));
			mostrarFichero(fichero);
		}
	}

	/**
	 * Metodo que lee un fichero enviado por parametro y recoge el contenido en una
	 * variable local. Posteriormente el valor de esa variable se introduce en el
	 * JTextArea del contenido de la nota
	 * 
	 * @param file
	 * @return Void
	 */
	private void mostrarFichero(File file) {
		int contadorLineas = 1;
		lblResultado.setText("");
		BufferedReader miFichero;
		String texto = "", linea = "", fecha = "";
		if (file.exists()) {
			textAreaNota.setText("");
			try {
				// Leemos el contenido de file y lo guardamos en el buffer miFichero
				FileReader fr = new FileReader(file);
				miFichero = new BufferedReader(fr);
				while ((linea = miFichero.readLine()) != null) {
					texto += linea + '\n';
					if (contadorLineas == 1) {
						fecha = linea;
						contadorLineas++;
					}
				}
				// Añadimos el contenido de texto al textAreaNota
				textAreaNota.setText(texto);
				// Sacamos la fecha de la nota de la primera linea del texto
				txtFecha.setText(fecha.substring(6, fecha.length() - 1));
				fr.close();
			} catch (IOException e) {
				lblResultado.setText("Error de Entrada/Salida");
			}
		} else
			lblResultado.setText("El fichero no existe");
	}

	/**
	 * Metodo que comprueba si un <strong>String</strong> contiene al final ".txt",
	 * devuelve true si el este contiene al final ".txt", false si el string aun no
	 * contiene ".txt"
	 * 
	 * @param nombreFichero
	 * @return Boolean
	 */
	private boolean comprobarNombreFichero(String nombreFichero) {
		String extensionFichero = (nombreFichero.substring(nombreFichero.length() - 4, nombreFichero.length()));
		if (extensionFichero.equals(".txt")) {
			return true;
		}
		return false;
	}

	/**
	 * Este metodo crea un fichero y un directorio. El directorio sera el encargado
	 * de guardar el fichero creado.
	 * 
	 * @return Void
	 */
	private void crearFichero() {
		String rutaDirectorio = System.getProperty("user.dir");
		// Se crea el directorio que guardara los siguentes ficheros creados
		File directorio = new File(rutaDirectorio + "/Diario_Notas");
		if (!directorio.exists()) {
			try {
				directorio.mkdirs();
			} catch (Exception e) {
				lblResultado.setText("Error al crear directorio");
				e.printStackTrace();
			}
		}
		JFileChooser fc = new JFileChooser(directorio.getAbsolutePath());
		String nombreArchivo = "";
		try {
			if (comprobarNombreFichero(txtRutaFichero.getText())) {
				nombreArchivo = txtRutaFichero.getText();
			} else {
				nombreArchivo = txtRutaFichero.getText() + ".txt";
			}
			// Se crea el fichero con nombreArchivo como nombre
			fc.setSelectedFile(new File(nombreArchivo));
			int seleccion = fc.showSaveDialog(panel);
			if (seleccion == JFileChooser.APPROVE_OPTION) {
				File fichero = fc.getSelectedFile();
				try {
					/*
					 * Se recoge el contenido del txtFecha y del textAreaNota y se escribe en una
					 * variable para generar el contenido del fichero
					 */
					FileWriter fw = new FileWriter(fichero);
					BufferedWriter bw = new BufferedWriter(fw);
					// Añadimos el dia en la primera linea del fichero y posteriormente el texto
					// restante
					String contenidoNota = "· Dia " + txtFecha.getText() + ":\n" + textAreaNota.getText();
					bw.write(contenidoNota);
					bw.close();
					fw.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		} catch (Exception e) {
			lblResultado.setText("El nombre del archivo no debe contener caracteres especiales.");
		}
	}

}
