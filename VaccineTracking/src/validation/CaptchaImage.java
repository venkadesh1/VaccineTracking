package validation;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/captchaimage")
public class CaptchaImage extends HttpServlet
{
	public void processRequest(HttpServletRequest req,HttpServletResponse res) throws IOException
	{
		res.setContentType("image/jpg");
		int itotalchar=6;
		int iheight=40;
		int iwidth=150;
		Font fntstyle1=new Font("Arial",Font.BOLD,30);
		Font fntstyle2=new Font("Arial",Font.BOLD,30);
		Random rndchars=new Random();
		String simagecode=(Long.toString(Math.abs(rndchars.nextLong()),36)).substring(0, itotalchar);
		BufferedImage biimage=new BufferedImage(iwidth,iheight,BufferedImage.TYPE_INT_RGB);
		Graphics2D g2dimage=(Graphics2D) biimage.getGraphics();
		int icircle=15;
		for(int i=0;i<icircle;i++)
		{
			g2dimage.setColor(new Color(rndchars.nextInt(255),rndchars.nextInt(255),rndchars.nextInt(255)));
			int iradius=(int) (Math.random()*iheight/2.0);
			int ix=(int) (Math.random()*iwidth-iradius);
			int iy=(int) (Math.random()*iheight-iradius);
		}
		g2dimage.setFont(fntstyle1);
		for(int i=0;i<itotalchar;i++)
		{
			g2dimage.setColor(new Color(rndchars.nextInt(255),rndchars.nextInt(255),rndchars.nextInt(255)));
			if(i%2==0)
			{
				g2dimage.drawString(simagecode.substring(i,i+1), 25*i, 24);
			}
			else
			{
				g2dimage.drawString(simagecode.substring(i,i+1), 25*i, 35);
			}
		}
		OutputStream osimage=res.getOutputStream();
		ImageIO.write(biimage, "jpeg", osimage);
		g2dimage.dispose();
		HttpSession session=req.getSession();
		session.setAttribute("captcha_security", simagecode.toString());
		//Cookie cookie = new Cookie("captcha_security", simagecode);
		//res.addCookie(cookie);
	}
	public void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException
	{
		processRequest(req,res);
	}
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException
	{
		processRequest(req,res);
	}
}