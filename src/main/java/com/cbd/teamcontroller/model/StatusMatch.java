package com.cbd.teamcontroller.model;

public enum StatusMatch {
	GANADO ("Ganado"), 
	EMPATADO("Empatado"), 
	PERDIDO ("Perdido"), 
	PENDIENTE ("Pendiente");
	
	
    private String type;

    private StatusMatch(final String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
    
	@Override
	public String toString() {
		return this.type;
	}

}
