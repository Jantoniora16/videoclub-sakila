package com.jantoniora16.videoclub.modelo;

public class Pelicula {

	private Integer id;
	private String titulo;
	private String descripcion;
	private Integer duracion;
	private String clasificacion;
	private Integer estreno;
	private String idioma;
	
	
	// Constructor vacío
    public Pelicula() {}

    	
	public Pelicula (Integer id, String titulo, String descripcion, Integer duracion, String clasificacion, Integer estreno, String idioma) {
		this.id=id;
		this.titulo=titulo;
		this.descripcion=descripcion;
		this.duracion=duracion;
		this.clasificacion=clasificacion;
		this.estreno=estreno;
		this.idioma=idioma;
	}
	
	
	
	
	
    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public int getDuracion() { return duracion; }
    public void setDuracion(int duracion) { this.duracion = duracion; }

    public String getClasificacion() { return clasificacion; }
    public void setClasificacion(String clasificacion) { this.clasificacion = clasificacion; }
	
    public Integer getEstreno() { return estreno; }
    public void setEstreno(Integer estreno) { this.estreno = estreno; }
    
    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }

}