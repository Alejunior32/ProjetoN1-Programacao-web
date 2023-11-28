import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import LoginPage from './LoginPage/LoginPage';
import EtiquetasPage from './EtiquetasPage/EtiquetasPage';
import NewEtiqueta from './NewEtiqueta/NewEtiqueta';
import NoticiaEtiquetas from './NoticiaEtiqueta/NoticiaEtiqueta';
import NoticiaGeralDia from './NoticiaGeralDia/NoticiaGeralDia';
import NoticiaGeralData from './NoticialGeralData/NoticiaGeralData';
import NoticiaEtiquetaData from './NoticiaEtiquetaData/NoticiaEtiquetaData';
import EtiquetasHistorico from './EtiquetasHistorico/EtiquetasHistorico';
import Usuarios from './Usuarios/Usuarios';
import DetalhesUsuario from './DetalhesUsuario/DetalhesUsuario';
import NovoUsuario from './NovoUsuario/NovoUsuario';
import EditarUsuario from './EditarUsuario/EditarUsuario';
import ListaAdmin from './ListaAdmin/ListaAdmin';
import DetalhesAdmin from './DetalhesAdmin/DetalhesAdmin';
import NovoAdmin from './NovoAdmin/NovoAdmin';
import EditarAdmin from './EditarAdmin/EditarAdmin';

const App = () => {
 const token = sessionStorage.getItem('token');

 return (
    <Router>
      <Routes>
        <Route path="/" element={<LoginPage />} />
        <Route path="etiquetas" element={<EtiquetasPage token={token} />} />
        <Route path="etiquetas/create" element={<NewEtiqueta token={token} />} />
        <Route path="etiquetas/historico" element={<EtiquetasHistorico token={token} />} />
        <Route path="/noticias/dia" element={<NoticiaGeralDia token={token} />} />
        <Route path="/noticias/:idEtiqueta" element={<NoticiaEtiquetas token={token} />} />
        <Route path="/noticias/:idEtiqueta/:selectedDate" element={<NoticiaEtiquetaData token={token} />} />
        <Route path="/noticias/data/:selectedDate" element={<NoticiaGeralData token={token} />} />
        <Route path="/admin/usuarios" element={<Usuarios token={token} />} />
        <Route path="/admin/usuarios/novoUsuario" element={<NovoUsuario token={token} />} />
        <Route path="/admin/usuarios/detalhes/:idUsuario" element={<DetalhesUsuario token={token} />} />
        <Route path="/admin/usuarios/editar/:idUsuario" element={<EditarUsuario token={token} />} />
        <Route path="/admin/admins" element={<ListaAdmin token={token} />} />
        <Route path="/admin/admins/novoAdmin" element={<NovoAdmin token={token} />} />
        <Route path="/admin/admins/detalhes/:idAdmin" element={<DetalhesAdmin token={token} />} />
        <Route path="/admin/admins/editar/:idAdmin" element={<EditarAdmin token={token} />} />

      </Routes>
    </Router>
 );
};

export default App;