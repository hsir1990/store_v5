package cn.itcast.store.service.serviceImp;

import java.util.List;
import cn.itcast.store.dao.ProductDao;
import cn.itcast.store.dao.daoImp.ProductDaoImp;
import cn.itcast.store.domain.PageModel;
import cn.itcast.store.domain.Product;
import cn.itcast.store.service.ProductService;

public class ProductServiceImp implements ProductService {

	ProductDao productDao = new ProductDaoImp();
	@Override
	public List<Product> findHots() throws Exception {
		
		return productDao.findHots();
	}

	@Override
	public List<Product> findNews() throws Exception {
		
		return  productDao.findNews();
	}

	@Override
	public Product findProductByPid(String pid) throws Exception {
		
		return productDao.findProductByPid(pid);
	}

	@Override
	public PageModel findProductsByCidWithPage(String cid, int curNum) throws Exception {
		//1_创建PageModel对象目的：计算分页参数
		//统计当前分类下商品的个数 select count(*) from product where cid=?
		int totalRecords = productDao.findTotalRecords(cid);
		PageModel pm = new PageModel(curNum, totalRecords,12);
		//2_关联集合 select * from product where cid =? limit ?, ?
		List list = productDao.findProductsByCidWithPage(cid, pm.getStartIndex(), pm.getPageSize());
		pm.setList(list);
		//3_关联url
		pm.setUrl("ProductServlet?method=findProductsByCidWithPage&cid="+cid );
		
//		return productDao.findProductsByCidWithPage(cid, curNum);
		return pm;
//		//1_创建PageModel对象 目的:计算分页参数
//		//统计当前分类下商品个数  select count(*) from product where cid=?
//		int totalRecords=ProductDao.findTotalRecords(cid);
//		PageModel pm=new PageModel(curNum,totalRecords,12);
//		//2_关联集合 select * from product where cid =? limit ? ,?
//		List list=ProductDao.findProductsByCidWithPage(cid,pm.getStartIndex(),pm.getPageSize());
//		pm.setList(list);
//		//3_关联url
//		pm.setUrl("ProductServlet?method=findProductsByCidWithPage&cid="+cid);
//		return pm;
	}

	@Override
	public PageModel findAllProductsWithPage(int curNum) throws Exception {
		//分页三件事
		//1创建对象           三个参数，当前页，总条数，每页多少条
		int totalRecords = productDao.findTotalRecords();
		PageModel pm = new PageModel(curNum,totalRecords, 5);
		//2关联集合          select * from product limit ?, ?
		List<Product> list = productDao.findAllProductsWithPage(pm.getStartIndex(), pm.getPageSize());
		pm.setList(list);
		//3关联url
		pm.setUrl("AdminProductServlet?method=findAllProductsWithPage");
		return pm;
	}

}
