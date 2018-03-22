import java.awt.PageAttributes.ColorType;



/**
 * Classe PixelMapPlus
 * Image de type noir et blanc, tons de gris ou couleurs
 * Peut lire et ecrire des fichiers PNM
 * Implemente les methodes de ImageOperations
 * @author : 
 * @date   : 
 */

public class PixelMapPlus extends PixelMap implements ImageOperations 
{
	/**
	 * Constructeur creant l'image a partir d'un fichier
	 * @param fileName : Nom du fichier image
	 */
	PixelMapPlus(String fileName)
	{
		super( fileName );
	}
	
	/**
	 * Constructeur copie
	 * @param type : type de l'image a creer (BW/Gray/Color)
	 * @param image : source
	 */
	PixelMapPlus(PixelMap image)
	{
		super(image); 
	}
	
	/**
	 * Constructeur copie (sert a changer de format)
	 * @param type : type de l'image a creer (BW/Gray/Color)
	 * @param image : source
	 */
	PixelMapPlus(ImageType type, PixelMap image)
	{
		super(type, image); 
	}
	
	/**
	 * Constructeur servant a allouer la memoire de l'image
	 * @param type : type d'image (BW/Gray/Color)
	 * @param h : hauteur (height) de l'image 
	 * @param w : largeur (width) de l'image
	 */
	PixelMapPlus(ImageType type, int h, int w)
	{
		super(type, h, w);
	}
	
	/**
	 * Genere le negatif d'une image
	 */
	public void negate()
	{
		for(int row=0; row<height; row++)
			for(int col=0; col<width; col++)
				imageData[row][col] = imageData[row][col].Negative();
	}
	
	/**
	 * Convertit l'image vers une image en noir et blanc
	 */
	public void convertToBWImage()
	{
		this.imageData = this.toBWImage().imageData;
	}
	
	/**
	 * Convertit l'image vers un format de tons de gris
	 */
	public void convertToGrayImage()
	{
		this.imageData = this.toGrayImage().imageData;
	}
	
	/**
	 * Convertit l'image vers une image en couleurs
	 */
	public void convertToColorImage()
	{
		this.imageData = this.toColorImage().imageData;
	}
	
	public void convertToTransparentImage()
	{
		this.imageData = this.toTransparentImage().imageData;
	}
	
	/**
	 * Fait pivoter l'image de 10 degres autour du pixel (row,col)=(0, 0)
	 * dans le sens des aiguilles d'une montre (clockWise == true)
	 * ou dans le sens inverse des aiguilles d'une montre (clockWise == false).
	 * Les pixels vides sont blancs.
	 * @param clockWise : Direction de la rotation 
	 */
	public void rotate(int x, int y, double angleRadian)
	{
		PixelMapPlus newPM = new PixelMapPlus(imageType, height, width);
		double cos = Math.cos(angleRadian);
		double sin = Math.sin(angleRadian);
		for(int row=0; row<height; row++)
		{
			for(int col=0; col<width; col++)
			{	
				int row2 = (int)(cos*(double)row - sin*(double)col - cos*(double)x+sin*(double)y+(double)x);
				int col2 = (int)(sin*(double)row + cos*(double)col - sin*(double)x-cos*(double)y+(double)y);
				if(row2 >= 0 && col2 >=0 && row2 < height && col2 < width)
					newPM.imageData[row][col] = imageData[row2][col2];
			}
		}
		imageData = newPM.imageData;
	}
	
	/**
	 * Modifie la longueur et la largeur de l'image 
	 * @param w : nouvelle largeur
	 * @param h : nouvelle hauteur
	 */
	public void resize(int w, int h) throws IllegalArgumentException
	{
		if(w < 0 || h < 0)
			throw new IllegalArgumentException();
		
		PixelMapPlus newPM = new PixelMapPlus(imageType, h, w);
		
		if(h >= height && w >= width) {
			int ratioh = h/height;
			int ratiow = w/width;
			
			for(int row=0; row<height; row++)
			{
				for(int col=0; col<width; col++)
				{	
					for(int i = 0; i< ratioh; i++) 
					{
						for(int j = 0; j< ratiow; j++) {
							newPM.imageData[row * ratioh + i][col * ratiow + j] = imageData[row][col];
						}
					}
				}
			}
		}
		else {
			int ratioh = height/h;
			int ratiow = width/w;
			
			for(int row=0; row<height; row+=ratioh)
			{
				for(int col=0; col<width; col+=ratiow)
				{	
					newPM.imageData[row/ratioh][col/ratiow] = imageData[row][col];
				}
			}
		}

		imageData = newPM.imageData;		
		
		height = h;
		width = w;
		
	}
	
	/**
	 * Insert pm dans l'image a la position row0 col0
	 */
	public void inset(PixelMap pm, int row0, int col0)
	{
		for(int row=row0; row<row0+pm.height; row++) {
			if (row <= height) {
				for(int col=col0; col<col0+pm.width; col++) {
					if(col <= width)
						imageData[row][col] = pm.imageData[row-row0][col-col0];
				}
			}
		}
			
	}
	
	/**
	 * Decoupe l'image 
	 */
	public void crop(int h, int w)
	{
		if(w < 0 || h < 0)
		throw new IllegalArgumentException();
		
		PixelMapPlus newPM = new PixelMapPlus(imageType, h, w);
		
		for(int row=0; row<h; row++)
		{
			for(int col=0; col<w; col++)
			{	
				if(row < height && col < width)
					newPM.imageData[row][col] = imageData[row][col];
			}
		}
		height = h;
		width = w;
		imageData = newPM.imageData;
	}
	
	/**
	 * Effectue une translation de l'image 
	 */
	public void translate(int rowOffset, int colOffset)
	{
		PixelMapPlus newPM = new PixelMapPlus(imageType, height, width);
		
		for(int row=0; row<height; row++)
		{
			for(int col=0; col<width; col++)
			{	
				if(row+rowOffset >= 0 && row+rowOffset < height && col+colOffset >= 0 && col+colOffset < width)
					newPM.imageData[row+rowOffset][col+colOffset] = imageData[row][col];
			}
		}
		imageData = newPM.imageData;		
	}
	
	/**
	 * Effectue un zoom autour du pixel (x,y) d'un facteur zoomFactor 
	 * @param x : colonne autour de laquelle le zoom sera effectue
	 * @param y : rangee autour de laquelle le zoom sera effectue  
	 * @param zoomFactor : facteur du zoom a effectuer. Doit etre superieur a 1
	 */
	public void zoomIn(int x, int y, double zoomFactor) throws IllegalArgumentException
	{
		if(zoomFactor < 1.0)
			throw new IllegalArgumentException();
		PixelMapPlus newPM = new PixelMapPlus(imageType, height, width);
		int ratio = (int)zoomFactor;
		int row2 = 0;
		
		for(int row= y - height/ratio/2; row < y + height/ratio/2; row++)
		{
			int col2 = 0;
			for(int col= x - width/ratio/2; col < x + width/ratio/2; col++)
			{	
				for(int i = 0; i< ratio; i++) 
				{
					for(int j = 0; j< ratio; j++) {
						if(row >= 0 && col >=0 && row < height && col < width) {
							newPM.imageData[row2 * ratio + i][col2 * ratio + j] = imageData[row][col];
						}
					}
				}
				if (col >= 0) col2++;
			}
			if (row >= 0) row2++;
		}
		
		imageData = newPM.imageData;
	}
}
