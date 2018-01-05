package webApplication.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

public class CSVReadInsertServlet extends CommonServlet {

	@Override
	protected void doServlet(HttpServletRequest req, HttpServletResponse resp, HttpSession hpSession, Logger logger)
			throws ServletException, IOException {

		logger.info("CSVの内容を登録開始します。");



		//JSPからファイルを受けとるったさいに保存する場所を指定
		String path = getServletContext().getRealPath("files");

		//ServletFileUploadオブジェクトを生成
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);

	     //アップロードする際の基準値を設定
		factory.setSizeThreshold(1024);
		upload.setSizeMax(-1);
		upload.setHeaderEncoding("Windows-31J");

        try {
//            (4)ファイルデータ(FileItemオブジェクト)を取得し、
//               Listオブジェクトとして返す
            List list = upload.parseRequest(req);
//
            //(5)ファイルデータ(FileItemオブジェクト)を順に処理
            Iterator iterator = list.iterator();
            while(iterator.hasNext()){
                FileItem fItem = (FileItem)iterator.next();

                //(6)ファイルデータの場合、if内を実行
                if(!(fItem.isFormField())){
                    //(7)ファイルデータのファイル名(PATH名含む)を取得
                    String fileName = fItem.getName();
                    if((fileName != null) && (!fileName.equals(""))){
                        //(8)PATH名を除くファイル名のみを取得
                        fileName=(new File(fileName)).getName();
                        //(9)ファイルデータを指定されたファイルに書き出し
                        fItem.write(new File(path + "/" + fileName));
                    }
                }
            }

                        //ファイルを読み込むJSPからファイルを取得する→pahtに一度格納しているのでそれを利用する
            			File file = new File(req.getParameter("filefd"));
        				String filePath = req.getParameter("filefd");
 						FileReader fr = new FileReader(path);
                        BufferedReader br = new BufferedReader(fr);


                        //読み込んだファイルを１行ずつ処理する
                        String line;
                        StringTokenizer token;
                        while ((line = br.readLine()) != null) {
                            //区切り文字","で分割する
                            token = new StringTokenizer(line, ",");

                            //分割した文字を画面出力する
                            while (token.hasMoreTokens()) {
                                System.out.println(token.nextToken());
                            }
                            System.out.println("**********");
                        }

                        //終了処理
                        br.close();

        }catch (FileUploadException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
			e.printStackTrace();
		}
	}

}

