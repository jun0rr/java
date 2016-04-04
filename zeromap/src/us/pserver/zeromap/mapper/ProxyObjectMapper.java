package us.pserver.zeromap.mapper;

import us.pserver.zeromap.MapperFactory;
import us.pserver.zeromap.Node;
import us.pserver.zeromap.ObjectBuilder;
import us.pserver.zeromap.ObjectMappingException;


/**
 *
 * @author juno
 */
public class ProxyObjectMapper extends AbstractObjectMapper {
	
	
	public ProxyObjectMapper() {
		super();
	}
	
	
	public ProxyObjectMapper(MapperFactory mfac) {
		super(mfac);
	}
	
	
	protected Object build(Class cls, Node node) {
		try {
			ObjectBuilder bld = ObjectBuilder.proxyBuilder();
			if(factory.builders().containsKey(cls)) {
				bld = factory.builders().get(cls);
			}
			return bld.create(cls, node);
		} catch(ReflectiveOperationException e) {
			throw new ObjectMappingException(e);
		}
	}

}
