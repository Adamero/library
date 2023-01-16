package pl.edu.wat.testowy.reflection;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodCall;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.pool.TypePool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class Reflection {
    //typedefinition jest podklasa typedescription
    private TypeDescription entityDefinition;
    private TypeDescription requestDefinition;
    private TypeDescription responseDefinition;

    private TypeDescription mapperDefinition;
    private TypePool typePool;
    private ByteBuddy byteBuddy;

    public Reflection() {
        this.typePool = TypePool.Default.ofSystemLoader();
        this.byteBuddy = new ByteBuddy();
        this.entityDefinition = typePool.describe("pl.edu.wat.testowy.entity.Author").resolve();
        this.requestDefinition = typePool.describe("pl.edu.wat.testowy.dto.AuthorRequest").resolve();
        this.responseDefinition = typePool.describe("pl.edu.wat.testowy.dto.AuthorResponse").resolve();
        this.mapperDefinition = typePool.describe("pl.edu.wat.testowy.mapper.AuthorMapper").resolve();

    }


    //nie beda to pola final bo one beda sie zmienialy
    public static void apply(String test, String fieldType) {

        var ref = new Reflection();
        ref.applyEntity(test, fieldType);
        ref.applyRequest(test, fieldType);
        ref.applyResponse(test, fieldType);
        ref.applyAuthorMapper(test);

    }

    public List<String> getFieldNames() {
        FieldInformation2.readJson("fields2.json");
        List<FieldInformation2> fields = FieldInformation2.getFields();
        List<String> fieldNames = new ArrayList<>();
        fields.forEach(f -> fieldNames.add(f.getFieldName()));
        return fieldNames;
    }

    private void applyAuthorMapper(String test) {//musimy wyciagnac mappera

        DynamicType.Builder<Object> builder = byteBuddy
                .redefine(mapperDefinition,
                        ClassFileLocator.ForClassLoader.ofSystemLoader())
                .method(named("fillAuthorRequest"))
                .intercept(MethodCall.invoke(setterAuthorEntity(test))
                        .onArgument(0)
                        .withMethodCall(MethodCall
                                .invoke(getterRequest(test))
                                .onArgument(1)))
                .method(named("fillAuthor"))
                .intercept(MethodCall.invoke(setterAuthorResponse(test))
                        .onArgument(0)
                        .withMethodCall(MethodCall
                                .invoke(getterEntity(test))
                                .onArgument(1)));

//korzystamy z metody ktora sie nazywa fillauthorrequest
        try (var unloadedAuthor = builder.make()) {
            mapperDefinition = unloadedAuthor.load(ClassLoader.getSystemClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                    .getTypeDescription();

        } catch (IOException e) {
            throw new RuntimeException();
        }

    }
    //zaimplementowalismy to ze zostanie w momencie uruchomienia aplikacji
    //podmienione pole i zostanie dodane author.setRank(authorRequest.getRank());
    //gdy wykonamy save na autorze bedzie on posiadal w sobie rank

    private MethodDescription getterEntity(String test) {
        return entityDefinition
                .getDeclaredMethods()
                .filter(ElementMatchers.isGetter(test))
                .stream()
                .findFirst()
                .orElseThrow();
    }

    private MethodDescription setterAuthorResponse(String test) {
        return responseDefinition
                .getDeclaredMethods()
                .filter(ElementMatchers.isSetter(test))
                .stream()
                .findFirst()
                .orElseThrow();
    }

    private MethodDescription getterRequest(String test) {
        return requestDefinition
                .getDeclaredMethods()
                .filter(ElementMatchers.isGetter(test))
                .stream()
                .findFirst()
                .orElseThrow();
        //metoda zwroci getter dla authorRequest dla pola rank
    }

    private MethodDescription setterAuthorEntity(String test) {
        return entityDefinition
                .getDeclaredMethods()
                .filter(ElementMatchers.isSetter(test))
                .stream()
                .findFirst()
                .orElseThrow();
        //bedzie wyciagala
        //type pool zwraca type description czyli klase opisujaca klase authorrequest,
        //bierzemy wszystkie metody filtrujemy i bierzemy te ktore jest getterem dla pola rank
        //zamieniamy to na stream i pieerwszy element znajdujemy element pierwszy
        //to jest optional
        //a jak nie to wyurzyca blad
        //setter rank request zwroci nam definicje metody setter w authorRequest
        //musimy ja wykonac na jakims argumencie

        //z entity wyciagamy set rank ktora musimy wykonac na autorze czyli autor jest naszym argumentem o indexie 0
        //pozniej musimy wyciagnac author.getrank, to jest kolejne wywolanie metody getrank na argumencie ktory jest indexie 1
    }

    private void applyResponse(String test, String fieldType) {

        DynamicType.Builder<Object> builder = byteBuddy
                .redefine(responseDefinition,
                        ClassFileLocator.ForClassLoader.ofSystemLoader())
                .defineProperty(test, typePool.describe(fieldType).resolve());

        try (var unloadedAuthor = builder.make()) {
            responseDefinition = unloadedAuthor.load(ClassLoader.getSystemClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                    .getTypeDescription();
//tu zostanie przypisana definicja nowej klasy

        } catch (IOException e) {
            throw new RuntimeException();
        }

    }

    private void applyRequest(String test, String fieldType) { //to jest dto ktore otrzymujemy
        DynamicType.Builder<Object> builder = byteBuddy
                .redefine(requestDefinition,
                        ClassFileLocator.ForClassLoader.ofSystemLoader())
                .defineProperty(test, typePool.describe(fieldType).resolve());

        try (var unloadedAuthor = builder.make()) {
            requestDefinition = unloadedAuthor.load(ClassLoader.getSystemClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                    .getTypeDescription();
//tu zostanie przypisana definicja nowej klasy

        } catch (IOException e) {
            throw new RuntimeException();
        }

    }

    public void applyEntity(String test, String fieldType) {
        DynamicType.Builder<Object> builder = byteBuddy
                .redefine(entityDefinition,
                        ClassFileLocator.ForClassLoader.ofSystemLoader())
                .defineProperty(test, typePool.describe(fieldType).resolve());

        try (var unloadedAuthor = builder.make()) {
            entityDefinition = unloadedAuthor.load(ClassLoader.getSystemClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                    .getTypeDescription();
//tu zostanie przypisana definicja nowej klasy author

        } catch (IOException e) {
            throw new RuntimeException();
        }

    }


}

//wszedzie na sztywno jest dodany rank czyli przez "rank"
//nalezaloby to scustomizowac dodawanie tych pol w dowolny sposob, nazwe albo tez zeby byl dowolny typ
//ta konfiguracja musi pochodzic z jakiegos zrodla zewnetrznego, zeby nie byla bezposrednio powiazana z kodem tak jak teraz przez "rank"
//na przyklad plik json, plik textowy, najlepiej w bazie danych (ale nie musi byc), json gradle.properties
//nowe pole ktore zostanie dodane musi byc obslugiwane, json, ma dac mozliwosc dodawanie wielu pol do wielu encji,
//czyli to musi byc konfig ktory przyjmuje nazwe klasy do ktorej chce dodac pole, jakie pole i jego typ
//dodatkowym rozszerzeniem moze byc ze nie kazdego pole mozemy zwracac albo przyjmowac
//dodajemy pole wrazliwe, ktore chcemy przyjmowac w request ale nie chcemy dawac w response (na lepsza ocene)


/*

[
  {
    "className": "pl.edu.wat.testowy.entity.Author",
    "name": "noble",
    "type": "String"
  },
  {
    "className": "pl.edu.wat.testowy.entity.Book",
    "name": "price",
    "type": "Double"
  }
]
 */