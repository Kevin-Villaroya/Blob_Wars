package Code.Engine.Ia;

public class Noeud {
	public int alpha;
	public int beta;
	
	public int profondeur;
	public Etat[] terrains;
	public Etat terrain;
	
	public Noeud(int profondeur, Etat terrain, int alpha, int beta) {
		this.profondeur = profondeur;
		this.terrain = terrain;
		this.alpha = alpha;
		this.beta = beta;
	}
	
	public Noeud(Noeud noeud) {
		this.profondeur = noeud.profondeur;
		this.terrains = noeud.terrains;
		this.alpha = noeud.alpha;
		this.beta = noeud.beta;
		
		this.terrain = noeud.terrain;
		this.terrains = new Etat[noeud.terrains.length];
		
		for(int i=0; i<noeud.terrains.length; i++) {
			this.terrains[i] = noeud.terrains[i];
		}
	}
}
