package utils;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;


import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.PicturesTable;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;

import org.apache.poi.hwpf.usermodel.TableRow;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import stcpream.StaticSet;


import java.io.*;
import java.util.Set;
import java.util.TreeSet;

public class DocUtils {

    private static Document document = null;
    static{
        document = DocumentHelper.createDocument();
    }

    public static void main(String[] args) {
        File file = new File("C:/Users/css/Desktop/1.doc");//注意文档地址
        if(file.exists()){
            HWPFDocument doc;
            try {
                doc = new HWPFDocument(new FileInputStream(file));

                Range range = doc.getRange();
                wordToHtml(doc);
//                printRange(doc);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * word解析并导出html文件
     * @param doc
     */
    public static String wordToHtml(HWPFDocument doc){
        Element htmlElement = DocumentHelper.createElement("html");
        document.setRootElement(htmlElement);

        Element headElement = DocumentHelper.createElement("head");
        Element charSetElement = DocumentHelper.createElement("meta");
        charSetElement.addAttribute("http-equiv", "Content-Type");
        charSetElement.addAttribute("content", "text/html; charset=UTF-8");
        headElement.add(charSetElement);
        htmlElement.add(headElement);

        Element bodyElement = DocumentHelper.createElement("body");
        Element contentElement = formatRange(doc);//解析word
        bodyElement.add(contentElement);
        htmlElement.add(bodyElement);

        String docString = document.asXML();
        //writeFile(docString, "C:/Users/css/Desktop/12345.html");
        System.out.println(docString);
        return docString;
    }
    /**
     * 导出文件
     * @param content
     * @param path
     */
    @Deprecated
    private static void writeFile(String content, String path) {
        FileOutputStream fos = null;
        BufferedWriter bw = null;
        try {
            File file = new File(path);
            fos = new FileOutputStream(file);
            bw = new BufferedWriter(new OutputStreamWriter(fos,"UTF-8"));
            bw.write(content);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
                if (fos != null)
                    fos.close();
            } catch (IOException ie) {
            }
        }
    }
    /**
     * 解析word文档（包括table表格、图片、text文本内容）
     * @param doc
     * @return
     */
    private static Element formatRange(HWPFDocument doc){
        Element contentEle = DocumentHelper.createElement("div");

        Range range = doc.getRange();
        PicturesTable pt = doc.getPicturesTable();

        int pnum = range.numParagraphs();
        Paragraph paragraph = null;
        String text = null;

        Element divElement = null;
        CharacterRun run = null;
        int numCharacterRuns = 0;
        StringBuilder styleStr = null;

        for(int i=0;i<pnum;i++){
            paragraph = range.getParagraph(i);//段落
            styleStr = new StringBuilder();
            if(paragraph.isInTable()){//是否为table
                Table table = range.getTable(paragraph);
                divElement = formatTable(table);//解析table内容
                i += table.numParagraphs();//跳过table
                i--;
            }/*else if(paragraph.isInList()){//目前解析的word模板没用到也就没做
                System.out.println("handle paragrah list----------------");
            }*/else{
                text = paragraph.text();
                if(text != null && !"".equals(text)){
                    run = paragraph.getCharacterRun(0);
                    numCharacterRuns = paragraph.numCharacterRuns();
                    if(run != null && numCharacterRuns>0){
                        if(run.text().charAt(0)==0x01 && pt.hasPicture(run)){//图片
                            divElement = formatImg(pt.extractPicture(run, true));//解析图片，创建img节点，并导出图片
                        }else{//文本内容
                            divElement = DocumentHelper.createElement("div");
                            divElement.addText(text);
                            if(run.isBold()){//加粗样式
                                styleStr.append("font-weight:bold;");
                            }
                            if(styleStr.length()>0){
                                divElement.addAttribute("style", styleStr.toString());
                            }
                        }
                    }
                }
            }
            if(divElement != null){//添加段落节点
                contentEle.add(divElement);
            }
        }
        return contentEle;
    }

    /**
     * 解析table表格
     * @param table
     * @return
     */
    private static Element formatTable(Table table){
        Element tableElement = DocumentHelper.createElement("table");
        Element theadElement = DocumentHelper.createElement("thead");
        Element tbodyElement = DocumentHelper.createElement("tbody");

        int[] tableCellEdges = buildTableCellEdgesArray(table);//单元格边界

        int rownum = table.numRows();
        int maxColumns = Integer.MIN_VALUE;
        for ( int r = 0; r < rownum; r++ ){
            maxColumns = Math.max( maxColumns, table.getRow( r ).numCells() );
        }
        Element rowElement = null;
        TableRow row = null;
        int cellnum = 0;
        Element cellElement = null;
        TableCell cell = null;
        int rowSpan = 0;
        int colSpan = 0;

        tableElement.addAttribute("border", "1");
        tableElement.addAttribute("style", "border-spacing:0");

        for(int i=0;i<rownum;i++){
            row = table.getRow(i);
            if(row != null){
                rowElement = DocumentHelper.createElement("tr");
                cellnum = row.numCells();
                int currentEdgeIndex = 0;
                for(int j=0;j<cellnum;j++){
                    cell = row.getCell(j);
                    if(cell.isVerticallyMerged() && !cell.isFirstVerticallyMerged())
                    {
                        currentEdgeIndex += getNumberColumnsSpanned(tableCellEdges, currentEdgeIndex, cell );
                        continue;
                    }
                    if(cell != null){
                        if(row.isTableHeader()){
                            cellElement = DocumentHelper.createElement("th");
                        }else{
                            cellElement = DocumentHelper.createElement("td");
                        }
                        colSpan = getNumberColumnsSpanned( tableCellEdges, currentEdgeIndex, cell );//取得列合并数
                        currentEdgeIndex += colSpan;
                        if ( colSpan == 0 ){
                            continue;
                        }
                        if ( colSpan != 1 ){
                            cellElement.addAttribute( "colspan", String.valueOf( colSpan ) );
                        }
                        rowSpan = getNumberRowsSpanned( table, tableCellEdges, i, j, cell );//取得行合并数
                        if ( rowSpan > 1 ){
                            cellElement.addAttribute( "rowspan", String.valueOf( rowSpan ) );
                        }
                        cellElement.addText(cell.text());
                    }
                    if(cellElement != null){
                        rowElement.add(cellElement);
                    }
                }
            }
            if(row.isTableHeader()){
                theadElement.add(rowElement);
            }else{
                tbodyElement.add(rowElement);
            }
        }
        if(theadElement.hasContent()){
            tableElement.add(theadElement);
        }
        if(tbodyElement.hasContent()){
            tableElement.add(tbodyElement);
        }
        return tableElement;
    }
    /**
     * 解析table单元格边界
     * @param table
     * @return
     */
    private static int[] buildTableCellEdgesArray( Table table )
    {
        Set<Integer> edges = new TreeSet<Integer>();
        for ( int r = 0; r < table.numRows(); r++ )
        {
            TableRow tableRow = table.getRow( r );
            for ( int c = 0; c < tableRow.numCells(); c++ )
            {
                TableCell tableCell = tableRow.getCell( c );
                edges.add( Integer.valueOf( tableCell.getLeftEdge() ) );
                edges.add( Integer.valueOf( tableCell.getLeftEdge()
                        + tableCell.getWidth() ) );
            }
        }
        Integer[] sorted = edges.toArray( new Integer[edges.size()] );
        int[] result = new int[sorted.length];
        for ( int i = 0; i < sorted.length; i++ )
        {
            result[i] = sorted[i].intValue();
        }
        return result;
    }

    /**
     * 解析table列合并数
     * @param tableCellEdges
     * @param currentEdgeIndex
     * @param tableCell
     * @return
     */
    private static int getNumberColumnsSpanned( int[] tableCellEdges,
                                                int currentEdgeIndex, TableCell tableCell )
    {
        int nextEdgeIndex = currentEdgeIndex;
        int colSpan = 0;
        int cellRightEdge = tableCell.getLeftEdge() + tableCell.getWidth();
        while ( tableCellEdges[nextEdgeIndex] < cellRightEdge )
        {
            colSpan++;
            nextEdgeIndex++;
        }
        return colSpan;
    }
    /**
     * 解析table行合并数
     * @param table
     * @param tableCellEdges
     * @param currentRowIndex
     * @param currentColumnIndex
     * @param tableCell
     * @return
     */
    private static int getNumberRowsSpanned( Table table,
                                             final int[] tableCellEdges, int currentRowIndex,
                                             int currentColumnIndex, TableCell tableCell )
    {
        if ( !tableCell.isFirstVerticallyMerged() )
            return 1;
        final int numRows = table.numRows();
        int count = 1;
        for ( int r1 = currentRowIndex + 1; r1 < numRows; r1++ )
        {
            TableRow nextRow = table.getRow( r1 );
            if ( currentColumnIndex >= nextRow.numCells() )
                break;
            // we need to skip row if he don't have cells at all
            boolean hasCells = false;
            int currentEdgeIndex = 0;
            for ( int c = 0; c < nextRow.numCells(); c++ )
            {
                TableCell nextTableCell = nextRow.getCell( c );
                if ( !nextTableCell.isVerticallyMerged()
                        || nextTableCell.isFirstVerticallyMerged() )
                {
                    int colSpan = getNumberColumnsSpanned( tableCellEdges,
                            currentEdgeIndex, nextTableCell );
                    currentEdgeIndex += colSpan;
                    if ( colSpan != 0 )
                    {
                        hasCells = true;
                        break;
                    }
                }
                else
                {
                    currentEdgeIndex += getNumberColumnsSpanned(
                            tableCellEdges, currentEdgeIndex, nextTableCell );
                }
            }
            if ( !hasCells )
                continue;
            TableCell nextCell = nextRow.getCell( currentColumnIndex );
            if ( !nextCell.isVerticallyMerged()
                    || nextCell.isFirstVerticallyMerged() )
                break;
            count++;
        }
        return count;
    }
    /**
     * 解析图片，创建img节点，并导出图片
     * @param picture
     * @return
     */
    private static Element formatImg(Picture picture){
        Element imgElement = null;
        if(picture != null){
            String imgUrl = getImgUrl(picture.suggestFullFileName());
            exportImg(picture, imgUrl);
            imgElement = DocumentHelper.createElement("img");
            imgElement.addAttribute("src", imgUrl);
        }
        return imgElement;
    }
    /**
     * 获取图片保存位置
     * @param suggestedName
     * @return
     */
    private static String getImgUrl(String suggestedName){

        return StaticSet.getRootPath()+suggestedName;//注意图片导出地址
    }
    /**
     * 导出图片
     * @param picture
     * @param expUrl
     */
    private static void exportImg(Picture picture,String expUrl){
        if(picture != null && expUrl != null && !"".equals(expUrl))
            try {
                picture.writeImageContent(new FileOutputStream(expUrl));
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
