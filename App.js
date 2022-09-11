import React, { useState } from 'react';
import { NativeModules, Button, Image, View, TextInput, StyleSheet, Alert } from 'react-native';

const App = () => {
  const [image, setImage] = useState(null);
  const [name, setName] = useState('');
  const { MyQRCode } = NativeModules
  const onPress = () => {
    if (name.length <= 0) {
      Alert.alert("Vui lòng nhập tên")
    } else {
      MyQRCode.myQRCodeEvent(name, QRIMAGE, QRIMAGEFAILD)
    }
  };

  const QRIMAGE = (image) => {
    setImage(image)
  }

  const QRIMAGEFAILD = (er) => {
    console.log('er', er)
  }

  return (
    <View style={styles.container}>
      <TextInput
        placeholder='Vui lòng nhập tên'
        onChangeText={(txt) => setName(txt)}
        style={styles.input}
      />
      <Button
        title="Click To Generate QR Codes!"
        color="#841584"
        onPress={onPress}
      />
      <View style={styles.containerImage}>
        <Image style={styles.image} source={{ uri: `${image}` }} />
      </View>
    </View>

  );
};

export default App;

const styles = StyleSheet.create({
  container: {
    flex: 1
  },
  input: {
    borderWidth: 1,
    margin: 10,
    borderRadius: 5

  },
  containerImage: {
    flex: 1,
    justifyContent: 'center',
    alignContent: 'center'
  }
  ,
  image:
    { width: 350, height: 350 }
})