package service;

public abstract class InjectableFactory<T> {

    private T instance;

    protected abstract T createInstance();

    public T getInstance(){
        if(instance == null){
            instance = createInstance();
        }
        return instance;
    }

    public void injectInstance(T instance){
        this.instance = instance;
    }
}