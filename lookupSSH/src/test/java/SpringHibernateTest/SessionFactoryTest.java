package SpringHibernateTest;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class SessionFactoryTest {
	 private ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");  
     @Test  
     public void testSession()  
     {           
               SessionFactory sf = (SessionFactory) context.getBean("sessionFactory");       
               System.out.println("spring和hibernate整合成功");  
     }        
}
