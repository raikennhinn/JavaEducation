package webApplication.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class EncodingFilter implements Filter {

	// 初期化パラメータを格納する変数
	private String encoding = null;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// 初期化パラメータを取得して格納
		this.encoding = filterConfig.getInitParameter("encoding");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// リクエストクラスのキャスト
		HttpServletRequest req = (HttpServletRequest)request;

		// リクエストパラメータのエンコード
		req.setCharacterEncoding(this.encoding);

		// 次のフィルタを呼び出す
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

}
