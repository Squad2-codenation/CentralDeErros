package br.com.codenation.specifications;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import br.com.codenation.enums.EnvironmentEnum;
import br.com.codenation.enums.LevelEnum;

public class GenericSpecificationBuilder {

	public static <T> Specification<T> filterRecords(Map<Class<?>, Class<?>> params) {
		return new Specification<T>() {
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();

				Set<Entry<Class<?>, Class<?>>> set = params.entrySet();
				Iterator it = set.iterator();
				
				while (it.hasNext()) {
					Entry<String, String> entry = (Entry)it.next();
						try {
							Class<?> klass = root.get(entry.getKey()).getJavaType();

							if (klass.equals(String.class)) {
								predicates.add(cb.like(root.get(entry.getKey()), "%"+entry.getValue()+"%"));
							} else {
								predicates.add(cb.equal(root.get(entry.getKey()), castParameters(klass, entry.getValue())));
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
				
				return cb.and(predicates.toArray(new Predicate[] {}));
			}
		};
	}
	
	private static Object castParameters(Class<? extends Object> type, String param) {
		if (type.equals(EnvironmentEnum.class)) {
			return EnvironmentEnum.valueOf(param);
		} else if (type.equals(LevelEnum.class)) {
			return LevelEnum.valueOf(param);
		} if (type.equals(Integer.class)) {
			return Integer.parseInt(param);
		} else if (type.equals(Long.class)) {
			return Long.parseLong(param);
		} else if (type.equals(UUID.class)) {
			return UUID.fromString(param);
		} else if (type.equals(Boolean.class)) {
			return Boolean.parseBoolean(param);
		}
		return type;
	}
}
