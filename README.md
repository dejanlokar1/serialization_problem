# Serialization problem
Jackson serialization problem reproduced. Last working version is 2.9.2. Problem only occurs with ListMaps, other Map
implementations and Sequences work as expected. Using static typing e.g. by annotating the field with 
@JsonSerialize(typing = JsonSerialize.Typing.STATIC) does serialize the collection.

## Exception
```
[info]   com.fasterxml.jackson.databind.JsonMappingException: Failed to specialize base type scala.collection.immutable.Map<java.lang.String,java.lang.String> as scala.collection.immutable.ListMap$Node, problem: Type parameter #1/2 differs; can not specialize java.lang.String with 
java.lang.Object (through reference chain: SerializationTest$SampleCaseClass["map"])     
[info]   at com.fasterxml.jackson.databind.JsonMappingException.wrapWithPath(JsonMappingException.java:394)
[info]   at com.fasterxml.jackson.databind.JsonMappingException.wrapWithPath(JsonMappingException.java:353)    
[info]   at com.fasterxml.jackson.databind.ser.std.StdSerializer.wrapAndThrow(StdSerializer.java:316)
[info]   at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:727)
[info]   at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:155)                                                                                                                                                                                          
[info]   at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider._serialize(DefaultSerializerProvider.java:480)
[info]   at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.serializeValue(DefaultSerializerProvider.java:319)
[info]   at com.fasterxml.jackson.databind.ObjectMapper._configAndWriteValue(ObjectMapper.java:3905)            
[info]   at com.fasterxml.jackson.databind.ObjectMapper.writeValue(ObjectMapper.java:3201)           
[info]   at SerializationTest.serialize(SerializationTest.scala:67)                                               
[info]   ...                                                                                    
[info]   Cause: java.lang.IllegalArgumentException: Failed to specialize base type scala.collection.immutable.Map<java.lang.String,java.lang.String> as scala.collection.immutable.ListMap$Node, problem: Type parameter #1/2 differs; can not specialize java.lang.String with java.lang.
Object                                                                                                                     
[info]   at com.fasterxml.jackson.databind.type.TypeFactory._bindingsForSubtype(TypeFactory.java:432)
[info]   at com.fasterxml.jackson.databind.type.TypeFactory.constructSpecializedType(TypeFactory.java:401)
[info]   at com.fasterxml.jackson.databind.cfg.MapperConfig.constructSpecializedType(MapperConfig.java:297)
[info]   at com.fasterxml.jackson.databind.DatabindContext.constructSpecializedType(DatabindContext.java:161)
[info]   at com.fasterxml.jackson.databind.ser.BeanPropertyWriter._findAndAddDynamic(BeanPropertyWriter.java:893)                                                                                                                                                                         
[info]   at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:705)
[info]   at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:719)
[info]   at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:155)          
[info]   at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider._serialize(DefaultSerializerProvider.java:480)
[info]   at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.serializeValue(DefaultSerializerProvider.java:319)
```

## Tests
``` sbt test ```
