package cn.itcast.store.dao.oracleDaoImp;

import java.util.List;

import cn.itcast.store.dao.ProductDao;
import cn.itcast.store.domain.Product;

public class OracleProductDaoImp implements ProductDao {

	@Override
	public List<Product> findHots() throws Exception {

		return null;
	}

	@Override
	public List<Product> findNews() throws Exception {

		return null;
	}

	@Override
	public Product findProductByPid(String pid) throws Exception {

		return null;
	}

	@Override
	public int findTotalRecords(String cid) throws Exception {

		return 0;
	}

	@Override
	public List findProductsByCidWithPage(String cid, int startIndex, int pageSize) throws Exception {

		return null;
	}

	@Override
	public int findTotalRecords() throws Exception {

		return 0;
	}

	@Override
	public List<Product> findAllProductsWithPage(int startIndex, int pageSize) throws Exception {

		return null;
	}

}
