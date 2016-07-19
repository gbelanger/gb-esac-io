package gb.esac.io;

import gb.esac.tools.BasicStats;
import gb.esac.tools.MinMax;
import gb.esac.tools.Stats;
import hep.aida.IAxis;
import hep.aida.IHistogram1D;
import hep.aida.ref.histogram.Histogram1D;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import org.apache.log4j.Logger;

/**
 * The class <code>AsciiDataFileWriter</code> is used to write data as ASCI files in QDP format.
 *
 * @author <a href="mailto: guilaume.belanger@esa.int">Guillaume Belanger</a>
 * @version 1.0 (June 2010, ESAC)
 */
public class AsciiDataFileWriter {

    // Class variables
    private static Logger logger  = Logger.getLogger(AsciiDataFileWriter.class);
    private PrintWriter printWriter;
    private static DecimalFormat stats = new DecimalFormat("0.00E00");
    private static DecimalFormat number = new DecimalFormat("0.000");

    //  Constructor
    public AsciiDataFileWriter(String filename) throws IOException {
	int bufferSize = 256000;
  	printWriter = new PrintWriter(new BufferedWriter(new FileWriter(filename), bufferSize));
    }

    //  Methods writeHisto for interface IHistogram1D

    public void writeHisto(IHistogram1D iHisto, String xLabel) {
	writeHisto((Histogram1D) iHisto, xLabel);
    }

    public void writeHisto(IHistogram1D iHisto, String xLabel, boolean showStats) {
	writeHisto((Histogram1D) iHisto, xLabel, showStats);
    }

    public void writeHisto(IHistogram1D iHisto, String xLabel, String yLabel, boolean showStats) {
	writeHisto((Histogram1D) iHisto, xLabel, yLabel, showStats);
    }

    public void writeHisto(IHistogram1D iHisto, String xLabel, String yLabel, String plotLabel) {
	writeHisto((Histogram1D) iHisto, xLabel, yLabel, plotLabel);
    }

    public void writeHisto(IHistogram1D iHisto, String xLabel, String yLabel, String plotLabel, boolean showStats) {
	writeHisto((Histogram1D) iHisto, xLabel, yLabel, plotLabel, showStats);
    }

    public void writeHisto(IHistogram1D iHisto, double yMin, double yMax, String xLabel) {
	writeHisto((Histogram1D) iHisto, yMin, yMax, xLabel);
    }

    public void writeHisto(IHistogram1D iHisto, double yMin, double yMax, String xLabel, boolean showStats) {
	writeHisto((Histogram1D) iHisto, yMin, yMax, xLabel, showStats);
    }

    public void writeHisto(IHistogram1D iHisto, double[] function, String xLabel) {
	writeHisto((Histogram1D) iHisto, function, xLabel);
    }

    public void writeHisto(IHistogram1D iHisto, double[] function, String xLabel, boolean showStats) {
	writeHisto((Histogram1D) iHisto, function, xLabel, showStats);
    }

    public void writeHisto(IHistogram1D iHisto, double[] function, String xLabel, String yLabel, boolean showStats) {
	writeHisto((Histogram1D) iHisto, function, xLabel, yLabel, showStats);
    }

    public void writeHisto(IHistogram1D iHisto, double[] function, String xLabel, String yLabel, String plotLabel) {
	writeHisto((Histogram1D) iHisto, function, xLabel, yLabel, plotLabel);
    }

    public void writeHisto(IHistogram1D iHisto, double[] function, String xLabel, String yLabel, String plotLabel, boolean showStats) {
	writeHisto((Histogram1D) iHisto, function, xLabel, yLabel, plotLabel, showStats);
    }


    //  Methods writeHisto for class Histogram1D

    public void writeHisto(Histogram1D histo, String xLabel) {
	boolean showStats = true;
	writeHisto(histo, xLabel, showStats);
    }

    public void writeHisto(Histogram1D histo, String xLabel, boolean showStats) {
	String[] header = makeHistoHeader(histo, xLabel, showStats);
	double[][] data = getData(histo);
	printToFile(header, data[0], data[1]);
    }

    public void writeHisto(Histogram1D histo, String xLabel, String yLabel, boolean showStats) {
	String plotLabel = "";
	writeHisto(histo, xLabel, yLabel, plotLabel, showStats);
    }

    public void writeHisto(Histogram1D histo, String xLabel, String yLabel, String plotLabel) {
	boolean showStats = true;	
	writeHisto(histo, xLabel, yLabel, plotLabel, showStats);
    }

    public void writeHisto(Histogram1D histo, String xLabel, String yLabel, String plotLabel, boolean showStats) {
	String[] header = makeHistoHeader(histo, xLabel, yLabel, plotLabel, showStats);
	double[][] data = getData(histo); 
	printToFile(header, data[0], data[1]);
    }

    public void writeHisto(Histogram1D histo, double yMin, double yMax, String xLabel) {
	boolean showStats = true;
	writeHisto(histo, yMin, yMax, xLabel, showStats);
    }

    public void writeHisto(Histogram1D histo, double yMin, double yMax, String xLabel, boolean showStats) {
	String[] header = makeHistoHeader(histo, yMin, yMax, xLabel, showStats);
	double[][] data = getData(histo); 
	printToFile(header, data[0], data[1]);
    }

    public void writeHisto(Histogram1D histo, double[] function, String xLabel) {
	boolean showStats = true;
	writeHisto(histo, function, xLabel, showStats);
    }

    public void writeHisto(Histogram1D histo, double[] function, String xLabel, boolean showStats) {
	String yLabel = "Entries per bin";
	writeHisto(histo, function, xLabel, yLabel, showStats);
    }

    public void writeHisto(Histogram1D histo, double[] function, String xLabel, String yLabel) {
	boolean showStats = true;
	writeHisto(histo, function, xLabel, yLabel, showStats);
    }

    public void writeHisto(Histogram1D histo, double[] function, String xLabel, String yLabel, boolean showStats) {
	String[] header = makeHistoHeader(histo, xLabel, yLabel, showStats);
	double[][] data = getData(histo);
	printToFile(header, data[0], data[1], function);
    }

    public void writeHisto(Histogram1D histo, double[] function, String xLabel, String yLabel, String plotLabel) {
	boolean showStats = true;
	writeHisto(histo, function, xLabel, yLabel, plotLabel, showStats);
    }

    public void writeHisto(Histogram1D histo, double[] function, String xLabel, String yLabel, String plotLabel, boolean showStats) {
	String[] header = makeHistoHeader(histo, xLabel, yLabel, plotLabel, showStats);
	double[][] data = getData(histo);
	printToFile(header, data[0], data[1], function);
    }

    public void writeCorrPlot(double[] x, double[] y, String xLabel, String yLabel) {
	double[] correlationCoeff = BasicStats.getCorrelationCoefficient(x, y);
	double r = correlationCoeff[0];
	double rSigma = correlationCoeff[1];
	String[] header = makeCorrPlotHeader(xLabel, yLabel, r, rSigma);
	printToFile(header, x, y);
    }

    // public void writeCorrPlot(double[] x, double[] y, double[] yErr, String xLabel, String yLabel) {
    // 	double[] correlationCoeff = BasicStats.getCorrelationCoefficient(x, y);
    // 	double r = correlationCoeff[0];
    // 	double rSigma = correlationCoeff[1];
    // 	String[] header = makeCorrPlotHeader(xLabel, yLabel, r, rSigma);
    // 	printToFile(header, x, y);
    // }

    public void writeCorrPlot(double[] x, double[] y, String xLabel, String yLabel, double[] xRange, double[] yRange, boolean logX, boolean logY) {
	double[] correlationCoeff = BasicStats.getCorrelationCoefficient(x, y);
	double r = correlationCoeff[0];
	double rSigma = correlationCoeff[1];
	String[] header = makeCorrPlotHeader(xLabel, yLabel, r, rSigma, xRange, yRange, logX, logY);
	printToFile(header, x, y);
    }


    //  Utility methods

    public static double[][] getData(Histogram1D histo) {
	IAxis axis = histo.axis();
	int nBins = axis.bins();
	double[] binHeights = new double[nBins];
	double[] binCentres = new double[nBins];
	for ( int i=0; i < nBins; i++ ) {
	    binHeights[i] = histo.binHeight(i);
	    binCentres[i] = axis.binCenter(i);
	}
	return new double[][] {binCentres, binHeights};
    }
    
    public static double[][] getData(IHistogram1D iHisto) {
	return getData((Histogram1D) iHisto);
    }

    private double[] calculateYMinYMax(Histogram1D histo) {
	// 	double maxBinHeight = histo.maxBinHeight();
	// 	double minBinHeight = histo.minBinHeight();
	// 	double max = Math.ceil(maxBinHeight/5d)*5d;
	// 	double margin = Math.ceil(0.05*max);
	// 	margin = Math.ceil(margin/5)*5;
	// 	double yMin = -margin;
	// 	double yMax = max + margin;
	// 	return new double[] { yMin, yMax };
	double maxBinHeight = histo.maxBinHeight();
	double minBinHeight = histo.minBinHeight();
	double margin = 0.05*maxBinHeight;
	double max = maxBinHeight + margin;
	double yMin = minBinHeight - margin;
	yMin = Math.max(0, yMin-margin);
	double yMax = max;
	return new double[] { yMin, yMax };
    }


    //  Make header methods

    public static String[] makeHeader(String xLabel, String yLabel, String plotLabel) {
	String[] header = new String[] {
	    "DEV /XS",
	    "LAB T", "LAB F",
	    "TIME OFF",
	    "LINE ON",
	    "MA 2 ON", "MA SIZE 1",
	    "LW 4", "CS 1.5",
	    "LAB X "+xLabel,
	    "LAB Y "+yLabel,
	    "LAB 1 \""+plotLabel+"\" CS 1.3",
	    "LAB 1 VPOS 0.27 0.8 JUST LEFT",
	    "VIEW 0.2 0.1 0.8 0.9",
	    "SKIP SINGLE",
	    "!"
	};
	return header;
    }

    public static String[] makeHeader(String xLabel, String yLabel) {
	String[] header = new String[] {
	    "DEV /XS",
	    "LAB T", "LAB F",
	    "TIME OFF",
	    "LINE ON",
	    "MA 2 ON", "MA SIZE 1",
	    "LW 4", "CS 1.5",
	    "LAB X "+xLabel,
	    "LAB Y "+yLabel,
	    "VIEW 0.2 0.1 0.8 0.9",
	    "SKIP SINGLE",
	    "!"
	};
	return header;
    }

    public static String[] makeCorrPlotHeader(String xLabel, String yLabel, double rho, double rhoErr) {
	String[] header = new String[] {
		"DEV /XS",
		"LAB T", "LAB F",
		"TIME OFF",
		"LINE OFF",
		"MA 1 ON",
		"MA SIZE 2",
		"LW 3", "CS 1.3",
		"VIEW 0.1 0.1 0.9 0.9",
		"LAB X "+xLabel,
		"LAB Y "+yLabel,
		"LAB 1 VPOS 0.2 0.8 \"\\gr = "+number.format(rho)+" +/- "+number.format(rhoErr)+"\"",
		"LAB 1 JUST LEFT",
		"!"
	};
	return header;
    }

    public static String[] makeCorrPlotHeader(String xLabel, String yLabel, double rho, double rhoErr, double[] xRange, double[] yRange, boolean logX, boolean logY) {
	String logXValue = "OFF";
	String logYValue = "OFF";
	if ( logX ) logXValue="ON";
	if ( logY ) logYValue="ON";
	String[] header = new String[] {
		"DEV /XS",
		"LAB T", "LAB F",
		"TIME OFF",
		"LINE OFF",
		"MA 1 ON",
		"MA SIZE 2",
		"LW 3", "CS 1.3",
		"VIEW 0.1 0.1 0.9 0.9",
		"LAB X "+xLabel,
		"LAB Y "+yLabel,
		"LAB 1 VPOS 0.2 0.8 \"\\gr = "+number.format(rho)+" +/- "+number.format(rhoErr)+"\"",
		"LAB 1 JUST LEFT",
		"LOG X "+logXValue,
		"LOG Y "+logYValue,
		"R X "+xRange[0]+" "+xRange[1],
		"R Y "+yRange[0]+" "+yRange[1],
		"!"
	};
	return header;
    }

    // PRIVATE methods

    private String[] makeHistoHeader(Histogram1D histo, String xLabel) {
	boolean showStats = true;
	return makeHistoHeader(histo, xLabel, showStats);
    }

    private String[] makeHistoHeader(Histogram1D histo, String xLabel, boolean showStats) {
	String yLabel = "Entries per bin";
	return makeHistoHeader(histo, xLabel, yLabel, showStats);
    }

    private String[] makeHistoHeader(Histogram1D histo, String xLabel, String yLabel) {
	boolean showStats = true;
	return makeHistoHeader(histo, xLabel, yLabel, showStats);
    }

    private String[] makeHistoHeader(Histogram1D histo, String xLabel, String yLabel, boolean showStats) {
	double[] yMinMax = calculateYMinYMax(histo);
	double yMin = yMinMax[0];
	double yMax = yMinMax[1];
	return makeHistoHeader(histo, yMin, yMax, xLabel, yLabel, showStats);
    }

    private String[] makeHistoHeader(Histogram1D histo, String xLabel, String yLabel, String plotLabel) {
	boolean showStats = true;
	return makeHistoHeader(histo, xLabel, yLabel, plotLabel, showStats);
    }

    private String[] makeHistoHeader(Histogram1D histo, String xLabel, String yLabel, String plotLabel, boolean showStats) {
	double[] yMinMax = calculateYMinYMax(histo);
	double yMin = yMinMax[0];
	double yMax = yMinMax[1];
	return makeHistoHeader(histo, yMin, yMax, xLabel, yLabel, plotLabel, showStats);
    }

    private String[] makeHistoHeader(Histogram1D histo, double yMin, double yMax, String xLabel, boolean showStats) {
	String yLabel = "Entries per bin";
	return makeHistoHeader(histo, yMin, yMax, xLabel, yLabel, showStats);
    }

    private String[] makeHistoHeader(Histogram1D histo, double yMin, double yMax, String xLabel, String yLabel, boolean showStats) {
	IAxis axis = histo.axis();
	int nBins = axis.bins();
	double binWidth = axis.binWidth(0);
	double lowerEdge = axis.binLowerEdge(0);
	double upperEdge = axis.binUpperEdge(nBins-1);
	double xRange = upperEdge - lowerEdge;
	double nMajorDivs = xRange/(2*binWidth);	
	String xMinStr = stats.format(lowerEdge);
	String xMaxStr = stats.format(upperEdge);
	String yMinStr = stats.format(yMin);
	String yMaxStr = stats.format(yMax);
	String[] header = null;
	if ( showStats == true ) {
	    int entries = histo.entries();
	    double mean = histo.mean();
	    double rms = histo.rms();
	    String meanStr = stats.format(mean);
	    String rmsStr = rmsStr = stats.format(rms);
	    header = new String[] {
		"DEV /XS",
		"READ 1",
		"LAB T", "LAB F",
		"TIME OFF",
		"LINE STEP",
		"LINE ON 3",
		"LW 4", "CS 1.5",
		"LAB 1 VPOS 0.76 0.8 \"Entries = "+entries+"\" JUST RIGHT CS 1",
		"LAB 2 VPOS 0.76 0.77 \"Mean = "+meanStr+"\" JUST RIGHT CS 1",
		"LAB 3 VPOS 0.76 0.74 \"RMS = "+rmsStr+"\" JUST RIGHT CS 1",
		"LAB X "+xLabel,
		"LAB Y "+yLabel,
		"VIEW 0.2 0.1 0.8 0.9",
		"R X "+xMinStr+" "+xMaxStr,
		"R Y "+yMinStr+" "+yMaxStr,
		//"GRID X "+nMajorDivs+",2",
		"!"
	    };
	}
	else {
	    header = new String[] {
		"DEV /XS",
		"READ 1",
		"LAB T", "LAB F",
		"TIME OFF",
		"LINE STEP",
		"LINE ON 3",
		"LW 4", "CS 1.5",
		"LAB X "+xLabel,
		"LAB Y "+yLabel,
		"VIEW 0.2 0.1 0.8 0.9",
		"R X "+xMinStr+" "+xMaxStr,
		"R Y "+yMinStr+" "+yMaxStr,
		//"GRID X "+nMajorDivs+",2",
		"!"
	    };
	}
	return header;
    }

    private String[] makeHistoHeader(Histogram1D histo, double yMin, double yMax, String xLabel, String yLabel,  String plotLabel, boolean showStats) {
	IAxis axis = histo.axis();
	int nBins = axis.bins();
	double binWidth = axis.binWidth(0);
	double lowerEdge = axis.binLowerEdge(0);
	double upperEdge = axis.binUpperEdge(nBins-1);
	double xRange = upperEdge - lowerEdge;
	double nMajorDivs = xRange/(2*binWidth);
	String xMinStr = stats.format(lowerEdge);
	String xMaxStr = stats.format(upperEdge);
	String yMinStr = stats.format(yMin);
	String yMaxStr = stats.format(yMax);
	String[] header = null;
	if ( showStats == true ) {
	    int entries = histo.entries();
	    double mean = histo.mean();
	    double rms = histo.rms();
	    String meanStr = null;
	    String rmsStr = null;
	    if ( Math.abs(mean) < 0.01 || Math.abs(mean) > 10 ) meanStr = stats.format(mean);
	    else meanStr = stats.format(mean);
	    if ( Math.abs(rms) < 0.01 || Math.abs(mean) > 10 ) rmsStr = stats.format(rms);
	    else rmsStr = stats.format(rms);
	    header = new String[] {
		"DEV /XS",
		"READ 1",
		"LAB T", "LAB F",
		"TIME OFF",
		"LINE STEP",
		"LINE ON 3",
		"LW 4", "CS 1.5",
		"LAB 1 VPOS 0.76 0.8 \"Entries = "+entries+"\" JUST RIGHT CS 1",
		"LAB 2 VPOS 0.76 0.77 \"Mean = "+meanStr+"\" JUST RIGHT CS 1",
		"LAB 3 VPOS 0.76 0.74 \"RMS = "+rmsStr+"\" JUST RIGHT CS 1",
		"LAB 4 \""+plotLabel+"\" CS 1",
		"LAB 4 VPOS 0.27 0.8 JUST LEFT",
		"LAB X "+xLabel,
		"LAB Y "+yLabel,
		"VIEW 0.2 0.1 0.8 0.9",
		"R X "+xMinStr+" "+xMaxStr,
		"R Y "+yMinStr+" "+yMaxStr,
		//"GRID X "+nMajorDivs+",2",
		"!"
	    };
	}
	else {
	    header = new String[] {
		"DEV /XS",
		"READ 1",
		"LAB T", "LAB F",
		"TIME OFF",
		"LINE STEP",
		"LINE ON 3",
		"LW 4", "CS 1.5",
		"LAB X "+xLabel,
		"LAB Y "+yLabel,
		"LAB 4 \""+plotLabel+"\" CS 1",
		"LAB 4 VPOS 0.27 0.8 JUST LEFT",
		"VIEW 0.2 0.1 0.8 0.9",
		"R X "+xMinStr+" "+xMaxStr,
		"R Y "+yMinStr+" "+yMaxStr,
		//"GRID X "+nMajorDivs+",2",
		"!"
	    };
	}
	return header;
    }

    private void printToFile(String[] header, double[] binCentres, double[] binHeights) {
	for ( int i=0; i < header.length; i++ ) {
	    printWriter.println(header[i]);
	}
	for ( int i=0; i < binCentres.length; i++ ) {
	    printWriter.println((binCentres[i]) +"\t"+ (binHeights[i]) +"\t");
	}
	printWriter.close();
    }

    private void printToFile(String[] header, double[] binCentres, double[] binHeights, DecimalFormat numberFormat) {
	for ( int i=0; i < header.length; i++ ) {
	    printWriter.println(header[i]);
	}
 	for ( int i=0; i < binCentres.length; i++ ) {
	    printWriter.println(numberFormat.format(binCentres[i]) +"\t"+ numberFormat.format(binHeights[i]) +"\t");
	}
	printWriter.close();
    }

    private void printToFile(String[] header, double[] binCentres, double[] binHeights, double[] function) {
	for ( int i=0; i < header.length; i++ ) {
	    printWriter.println(header[i]);
	}
      	for ( int i=0; i < binCentres.length; i++ ) {
	    printWriter.println((binCentres[i]) +"\t"+ (binHeights[i]) +"\t"+ function[i]);
	}
	printWriter.close();
    }


    // Methods writeData

    public void writeData(String[] header, double[] x, double[] y) throws IOException {
	for ( int i=0; i < header.length; i++ )  printWriter.println(header[i]);
	int nbins = (new Double(Math.min(x.length, y.length))).intValue();
	for ( int i=0; i < nbins; i++ ) {
	    printWriter.println((x[i]) +"\t"+ (y[i]) +"\t");
	}
	printWriter.close();
    }

    public void writeData(String[] header, double[] x, double[] y, int startIndex) throws IOException {
	for ( int i=0; i < header.length; i++ )  printWriter.println(header[i]);
	int nbins = (new Double(Math.min(x.length, y.length))).intValue();
	for ( int i=startIndex; i < nbins; i++ ) {
	    printWriter.println((x[i]) +"\t"+ (y[i]) +"\t");
	}
	printWriter.close();
    }

    public void writeData(String[] header, int[] x, double[] y) throws IOException {
	for ( int i=0; i < header.length; i++ )  printWriter.println(header[i]);
	int nbins = (new Double(Math.min(x.length, y.length))).intValue();
	for ( int i=0; i < nbins; i++ ) {
	    printWriter.println((x[i]) +"\t"+ (y[i]) +"\t");
	}
	printWriter.close();
    }

    public void writeData(String[] header, int[] x, int[] y) throws IOException {
	for ( int i=0; i < header.length; i++ )  printWriter.println(header[i]);
	int nbins = (new Double(Math.min(x.length, y.length))).intValue();
	for ( int i=0; i < nbins; i++ ) {
	    printWriter.println(x[i] +"\t"+ y[i] +"\t");
	}
	printWriter.close();
    }

    public void writeData(String[] header, int[] x, double[] y, double[] y2) throws IOException {
	for ( int i=0; i < header.length; i++ )  printWriter.println(header[i]);
	int lengths[] = new int[] {x.length, y.length, y2.length};
	int nbins = (new Double(MinMax.getMin(lengths))).intValue();
	for ( int i=0; i < nbins; i++ ) {
	    printWriter.println((x[i]) +"\t"+ (y[i]) +"\t"+ (y2[i]) +"\t");
	}
	printWriter.close();
    }

    public void writeData(String[] header, int[] x, double[] y, double[] y2, double[] y3) throws IOException {
	for ( int i=0; i < header.length; i++ )  printWriter.println(header[i]);
	int lengths[] = new int[] {x.length, y.length, y2.length, y3.length};
	int nbins = (new Double(MinMax.getMin(lengths))).intValue();
	for ( int i=0; i < nbins; i++ ) {
	    printWriter.println((x[i]) +"\t"+ (y[i]) +"\t"+ (y2[i]) +"\t"+ (y3[i]) +"\t");
	}
	printWriter.close();
    }

    public void writeData(String[] header, int[] x, double[] y, double[] y2, double[] y3, double[] y4) throws IOException {
	for ( int i=0; i < header.length; i++ )  printWriter.println(header[i]);
	int lengths[] = new int[] {x.length, y.length, y2.length, y3.length, y4.length};
	int nbins = (new Double(MinMax.getMin(lengths))).intValue();
	for ( int i=0; i < nbins; i++ ) {
	    printWriter.println((x[i]) +"\t"+ (y[i]) +"\t"+ (y2[i]) +"\t"+ (y3[i]) +"\t" +(y4[i]) +"\t");
	}
	printWriter.close();
    }

    public void writeData(String[] header, int[] x, double[] y, double[] y2, double[] y3, double[] y4, double[] y5) throws IOException {
	for ( int i=0; i < header.length; i++ )  printWriter.println(header[i]);
	int lengths[] = new int[] {x.length, y.length, y2.length, y3.length, y4.length, y5.length};
	int nbins = (new Double(MinMax.getMin(lengths))).intValue();
	for ( int i=0; i < nbins; i++ ) {
	    printWriter.println((x[i]) +"\t"+ (y[i]) +"\t"+ (y2[i]) +"\t"+ (y3[i]) +"\t" +(y4[i]) +"\t" +(y5[i]) +"\t");
	}
	printWriter.close();
    }

    public void writeData(String[] header, int[] col1, int[] col2, double[] col3) throws IOException {
	for ( int i=0; i < header.length; i++ )  printWriter.println(header[i]);
	int lengths[] = new int[] {col1.length, col2.length, col3.length};
	int nbins = (new Double(MinMax.getMin(lengths))).intValue();
	for ( int i=0; i < nbins; i++ ) {
	    printWriter.println((col1[i]) +"\t"+ (col2[i]) +"\t"+ (col3[i]) +"\t");
	}
	printWriter.close();
    }

    public void writeData(String[] header, String[] col1, int[] col2, double[] y) throws IOException {
	for ( int i=0; i < header.length; i++ )  printWriter.println(header[i]); 
	for ( int i=0; i < col1.length; i++ ) {
	    printWriter.println(col1[i] +"\t"+ (col2[i]) +"\t"+ (y[i]) +"\t");
	}
	printWriter.close();
    }

    public void writeData(String[] header, double[] c1, double[] c2, double[] c3) throws IOException {
	for ( int i=0; i < header.length; i++ )  printWriter.println(header[i]);
	int lengths[] = new int[] {c1.length, c2.length, c3.length};
	double var = BasicStats.getVariance(lengths);
	if ( var != 0 ) {
	    logger.warn("input column data of different lengths. Using min.");
	}
	int nbins = (new Double(MinMax.getMin(lengths))).intValue();
	for ( int i=0; i < nbins; i++ ) {
	    printWriter.println((c1[i]) +"\t"+ (c2[i]) +"\t"+ (c3[i]) +"\t");
	}
	printWriter.close();
    }

    public void writeData(String[] header, double[] c1, double[] c2, double[] c3, double[] c4) throws IOException {
	for ( int i=0; i < header.length; i++ )  printWriter.println(header[i]);
	int lengths[] = new int[] {c1.length, c2.length, c3.length, c4.length};
	double var = BasicStats.getVariance(lengths);
	if ( var != 0 ) {
	    logger.warn("input column data of different lengths. Using min.");
	}
	int nbins = (new Double(MinMax.getMin(lengths))).intValue();
	for ( int i=0; i < nbins; i++ ) {
	    printWriter.println((c1[i]) +"\t"+ (c2[i]) +"\t"+ 
				(c3[i]) +"\t"+ (c4[i]) +"\t");
	}
	printWriter.close();
    }
    
    public void writeData(String[] header, double[] c1, double[] c2, double[] c3, double[] c4, double[] c5) throws IOException {
	for ( int i=0; i < header.length; i++ )  printWriter.println(header[i]);
	int lengths[] = new int[] {c1.length, c2.length, c3.length, c4.length, c5.length};
	double var = BasicStats.getVariance(lengths);
	if ( var != 0 ) {
	    logger.warn("input column data of different lengths. Using min.");
	}
	int nbins = (new Double(MinMax.getMin(lengths))).intValue();
	for ( int i=0; i < nbins; i++ ) {
	    printWriter.println((c1[i]) +"\t"+ (c2[i]) +"\t"+ 
				(c3[i]) +"\t"+ (c4[i]) +"\t"+ 
				(c5[i]) +"\t");
	}
	printWriter.close();
    }

    public void writeData(String[] header, int[] c1, double[] c2, double[] c3, double[] c4, int[] c5) 	throws IOException {
	for ( int i=0; i < header.length; i++ )  printWriter.println(header[i]);
	int lengths[] = new int[] {c1.length, c2.length, c3.length, c4.length, c5.length};
	double var = BasicStats.getVariance(lengths);
	if ( var != 0 ) {
	    logger.warn("input column data of different lengths. Using min.");
	}
	int nbins = (new Double(MinMax.getMin(lengths))).intValue();
	for ( int i=0; i < nbins; i++ ) {
	    printWriter.println((c1[i]) +"\t"+ (c2[i]) +"\t"+ 
				(c3[i]) +"\t"+ (c4[i]) +"\t"+ 
				(c5[i]) +"\t");
	}
	printWriter.close();
    }

    public void writeData(String[] header, double[] c1, double[] c2, double[] c3, double[] c4, double[] c5, double[] c6) throws IOException {
	for ( int i=0; i < header.length; i++ )  printWriter.println(header[i]);
	int lengths[] = new int[] {c1.length, c2.length, c3.length, c4.length, c5.length, c6.length};
	double var = BasicStats.getVariance(lengths);
	if ( var != 0 ) {
	    logger.warn("input column data of different lengths. Using min.");
	}
	int nbins = (new Double(MinMax.getMin(lengths))).intValue();
	for ( int i=0; i < nbins; i++ ) {
	    printWriter.println((c1[i]) +"\t"+ (c2[i]) +"\t"+ 
				(c3[i]) +"\t"+ (c4[i]) +"\t"+
				(c5[i]) +"\t"+ (c6[i]) +"\t");
	}
	printWriter.close();
    }

    public void writeData(String[] header, double[] c1, double[] c2, double[] c3, double[] c4, double[] c5, double[] c6, double[] c7) throws IOException {
	for ( int i=0; i < header.length; i++ )  printWriter.println(header[i]);
	int lengths[] = new int[] {c1.length, c2.length, c3.length, c4.length, c5.length, c6.length, c7.length};
	double var = BasicStats.getVariance(lengths);
	if ( var != 0 ) {
	    logger.warn("input column data of different lengths. Using min.");
	}
	int nbins = (new Double(MinMax.getMin(lengths))).intValue();
	for ( int i=0; i < nbins; i++ ) {
	    printWriter.println((c1[i]) +"\t"+ (c2[i]) +"\t"+ 
				(c3[i]) +"\t"+ (c4[i]) +"\t"+
				(c5[i]) +"\t"+ (c6[i]) +"\t"+
				(c7[i]) +"\t");
	}
	printWriter.close();
    }

    public void writeData(String[] header, double[] c1, double[] c2, double[] c3, double[] c4, double[] c5, double[] c6, double[] c7, double[] c8) throws IOException {
	for ( int i=0; i < header.length; i++ )  printWriter.println(header[i]);
	int lengths[] = new int[] {c1.length, c2.length, c3.length, c4.length, c5.length, c6.length, c7.length, c8.length};
	double var = BasicStats.getVariance(lengths);
	if ( var != 0 ) {
	    logger.warn("input column data of different lengths. Using min.");
	}
	int nbins = (new Double(MinMax.getMin(lengths))).intValue();
	for ( int i=0; i < nbins; i++ ) {
	    printWriter.println((c1[i]) +"\t"+ (c2[i]) +"\t"+ 
				(c3[i]) +"\t"+ (c4[i]) +"\t"+
				(c5[i]) +"\t"+ (c6[i]) +"\t"+
				(c7[i]) +"\t"+ (c8[i]) +"\t");
	}
	printWriter.close();
    }

    public void writeData(String[] header, double[] c1, double[] c2, double[] c3, double[] c4, double[] c5, double[] c6, double[] c7, double[] c8, double[] c9) throws IOException {
	for ( int i=0; i < header.length; i++ )  printWriter.println(header[i]);
	int lengths[] = new int[] {c1.length, c2.length, c3.length, c4.length, c5.length, c6.length, c7.length, c8.length, c9.length};
	double var = BasicStats.getVariance(lengths);
	if ( var != 0 ) {
	    logger.warn("input column data of different lengths. Using min.");
	}
	int nbins = (new Double(MinMax.getMin(lengths))).intValue();
	for ( int i=0; i < nbins; i++ ) {
	    printWriter.println((c1[i]) +"\t"+ (c2[i]) +"\t"+ 
				(c3[i]) +"\t"+ (c4[i]) +"\t"+
				(c5[i]) +"\t"+ (c6[i]) +"\t"+
				(c7[i]) +"\t"+ (c8[i]) +"\t"+
				(c9[i]) +"\t");
	}
	printWriter.close();
    }
    public void writeData(String[] header, double[] c1, double[] c2, double[] c3, double[] c4, double[] c5, double[] c6, double[] c7, double[] c8, double[] c9, double[] c10) throws IOException {
	for ( int i=0; i < header.length; i++ )  printWriter.println(header[i]);
	int lengths[] = new int[] {c1.length, c2.length, c3.length, c4.length, c5.length, c6.length, c7.length, c8.length, c9.length, c10.length};
	double var = BasicStats.getVariance(lengths);
	if ( var != 0 ) {
	    logger.warn("input column data of different lengths. Using min.");
	}
	int nbins = (new Double(MinMax.getMin(lengths))).intValue();
	for ( int i=0; i < nbins; i++ ) {
	    printWriter.println((c1[i]) +"\t"+ (c2[i]) +"\t"+ 
				(c3[i]) +"\t"+ (c4[i]) +"\t"+
				(c5[i]) +"\t"+ (c6[i]) +"\t"+
				(c7[i]) +"\t"+ (c8[i]) +"\t"+
				(c9[i]) +"\t"+ (c10[i]) +"\t");
	}
	printWriter.close();
    }

    public void writeData(String[] header, double[] c1, double[] c2, double[] c3, double[] c4, double[] c5, double[] c6, double[] c7, double[] c8, double[] c9, double[] c10, double[] c11) throws IOException {
	for ( int i=0; i < header.length; i++ )  printWriter.println(header[i]);
	int lengths[] = new int[] {c1.length, c2.length, c3.length, c4.length, c5.length, c6.length, c7.length, c8.length, c9.length, c10.length, c11.length};
	double var = BasicStats.getVariance(lengths);
	if ( var != 0 ) {
	    logger.warn("input column data of different lengths. Using min.");
	}
	int nbins = (new Double(MinMax.getMin(lengths))).intValue();
	for ( int i=0; i < nbins; i++ ) {
	    printWriter.println((c1[i]) +"\t"+ (c2[i]) +"\t"+ 
				(c3[i]) +"\t"+ (c4[i]) +"\t"+
				(c5[i]) +"\t"+ (c6[i]) +"\t"+
				(c7[i]) +"\t"+ (c8[i]) +"\t"+
				(c9[i]) +"\t"+ (c10[i]) +"\t"+
				(c11[i]) +"\t");
	}
	printWriter.close();
    }

}
