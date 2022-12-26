package messenger.messenger.auth.oauth.application.converter;

public interface ProviderUserConverter<T, R> {

    R converter(T t);
}
