package code.shubham.commons.utils;

import code.shubham.commons.annotations.Role;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.util.Optional;

public class AspectUtils {

	public static <T extends Annotation> Optional<T> getClassAnnotation(final ProceedingJoinPoint joinPoint,
			final Class<T> clazz) {
		return Optional.ofNullable(joinPoint.getTarget().getClass().getAnnotationsByType(clazz))
			.filter(t -> t.length > 0)
			.map(t -> t[0]);
	}

	@SuppressWarnings("unchecked")
	public static <T> Optional<T> getMethodAnnotation(final ProceedingJoinPoint joinPoint, final Class<T> clazz) {
		for (final Annotation annotation : ((MethodSignature) joinPoint.getSignature()).getMethod()
			.getDeclaredAnnotations())
			if (annotation.annotationType().equals(clazz))
				return Optional.of((T) annotation);
		return Optional.empty();
	}

}
