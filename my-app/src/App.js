import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import LoginPage from './LoginPage/LoginPage';
import EtiquetasPage from './EtiquetasPage/EtiquetasPage';
import NewEtiqueta from './NewEtiqueta/NewEtiqueta';
import NoticiaEtiquetas from './NoticiaEtiqueta/NoticiaEtiqueta'
import NoticiaGeralDia from './NoticiaGeralDia/NoticiaGeralDia'
import NoticiaGeralData from './NoticialGeralData/NoticiaGeralData';


const App = () => {
 const token = sessionStorage.getItem('token');

 return (
    <Router>
      <Routes>
        <Route>
          <Route path="/" element={<LoginPage />} />
        </Route>
        <Route path="etiquetas" element={<EtiquetasPage token={token} />} />
        <Route path="etiquetas/create" element={<NewEtiqueta token={token} />} />
        <Route path="/noticias/:idEtiqueta" element={<NoticiaEtiquetas token={token} />} />
        <Route path="/noticias/dia" element={<NoticiaGeralDia token={token} />} />
        <Route path="/noticias/dia/:selectedDate" element={<NoticiaGeralData token={token} />} />
      </Routes>
    </Router>
 );
};

export default App;