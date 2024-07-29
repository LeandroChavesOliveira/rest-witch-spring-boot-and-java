package br.com.erudio.mapper;

import java.util.ArrayList;
import java.util.List;

//import org.modelmapper.ModelMapper;
//import br.com.erudio.data.vo.v1.PersonVO;
//import br.com.erudio.model.Person;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;


public class DozerMapper {
	
	//private static ModelMapper mapper = new ModelMapper();
	
//	static {
//		mapper.createTypeMap(
//				Person.class, 
//				PersonVO.class)
//		.addMapping(Person::getId , PersonVO::setKey);		
//		
//		mapper.createTypeMap(
//				PersonVO.class, 
//				Person.class)
//		.addMapping(PersonVO::getKey, Person::setId);
//		
//		
//	}

	private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();

	public static <O, D> D parseObject(O origen, Class<D> destination) {
		return mapper.map(origen, destination);
	}

	public static <O, D> List<D> parseListObjects(List<O> origen, Class<D> destination) {
		List<D> destinatinationObjects = new ArrayList<D>();
		for (O o : origen) {
			destinatinationObjects.add(mapper.map(o, destination));
		}
		return destinatinationObjects;
	}
}
