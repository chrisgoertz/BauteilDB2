package application;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Bauteil {
	private IntegerProperty Id = new SimpleIntegerProperty();
	private StringProperty Name = new SimpleStringProperty();
	private IntegerProperty Menge = new SimpleIntegerProperty();
	private StringProperty Ort = new SimpleStringProperty();
	private StringProperty Notes = new SimpleStringProperty();
	
	public Bauteil()
	{
		this.Id.set(-1);
		this.Name.set("name_not_set");
		this.Menge.set(0);
		this.Ort.set("ort_not_set");
		this.Notes.set("notes_not_set");
	}
	
	public Bauteil(String Name, int Menge, String Ort, String Notes) {
		//this.Id.set(Id);
		this.Name.set(Name);
		this.Menge.set(Menge);
		this.Ort.set(Ort);
		this.Notes.set(Notes);
		
	}
	

	public Bauteil(int Id, String Name, int Menge, String Ort, String Notes) {
		this.Id.set(Id);
		this.Name.set(Name);
		this.Menge.set(Menge);
		this.Ort.set(Ort);
		this.Notes.set(Notes);
		
	}

	public final IntegerProperty IdProperty() {
		return this.Id;
	}
	

	public final int getId() {
		return this.IdProperty().get();
	}
	

	public final void setId(final int Id) {
		this.IdProperty().set(Id);
	}
	

	public final StringProperty NameProperty() {
		return this.Name;
	}
	

	public final String getName() {
		return this.NameProperty().get();
	}
	

	public final void setName(final String Name) {
		this.NameProperty().set(Name);
	}
	

	public final IntegerProperty MengeProperty() {
		return this.Menge;
	}
	

	public final int getMenge() {
		return this.MengeProperty().get();
	}
	

	public final void setMenge(final int Menge) {
		this.MengeProperty().set(Menge);
	}
	

	public final StringProperty OrtProperty() {
		return this.Ort;
	}
	

	public final String getOrt() {
		return this.OrtProperty().get();
	}
	

	public final void setOrt(final String Ort) {
		this.OrtProperty().set(Ort);
	}
	

	public final StringProperty NotesProperty() {
		return this.Notes;
	}
	

	public final String getNotes() {
		return this.NotesProperty().get();
	}
	

	public final void setNotes(final String Notes) {
		this.NotesProperty().set(Notes);
	}
	
	@Override
	public final String toString() {
		
		return this.getId()+":"+this.getName();
		
	}
	
	
	
}
