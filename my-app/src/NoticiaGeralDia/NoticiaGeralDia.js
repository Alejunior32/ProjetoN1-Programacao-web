import React, { useState, useEffect } from 'react';
import './NoticiaGeralDia.css';
import { Link } from 'react-router-dom';

const NoticiaGeralDia = ({ token }) => {
  const [etiquetas, setEtiquetas] = useState([]);

  useEffect(() => {
    if (token && token.content && Array.isArray(token.content)) {
      const noticiasPromises = token.content.map(async (etiqueta) => {
        try {
          const noticiasResponse = await fetch(`localhost:8080/v1/noticias/usuario/todas-as-noticias-do-usuario/data-atual/`, {
            method: 'GET',
            headers: {
              'Authorization': `Bearer ${token}`,
              'Content-Type': 'application/json',
            },
          });
  
          if (noticiasResponse.ok) {
            const noticiasData = await noticiasResponse.json();
            return { ...etiqueta, noticias: noticiasData.items };
          } else {
            console.error('Erro ao buscar notícias para etiqueta:', noticiasResponse.status);
            return etiqueta;
          }
        } catch (error) {
          console.error('Erro durante a solicitação de notícias:', error);
          return etiqueta;
        }
      });
  
      Promise.all(noticiasPromises)
        .then((etiquetasWithNoticias) => setEtiquetas(etiquetasWithNoticias))
        .catch((error) => console.error('Erro ao processar promessas:', error));
    }
  }, [token]);


  return (
    <div className="container mt-4">
      <div className="noticia-square">
      {etiquetas.length > 0 ? (
        etiquetas.map((etiqueta) => (
          <div key={etiqueta.id} className="card mb-3">
            <div className="card-body">
              <h2 className="card-title">{etiqueta.titulo}</h2>
              <p className="card-text">{etiqueta.introducao}</p>
              <p>Data de Publicação: {etiqueta.data_publicacao}</p>
              <p>Editorias: {etiqueta.editorias}</p>
            </div>
            <hr className="my-0" />
          </div>
        ))
      ) : (
        <p className="alert alert-info">Não há notícias relacionadas a estas etiquetas na data de hoje!</p>
      )}
      <Link to="/etiquetas" className='btn btn-success'>
            Voltar
          </Link>
      </div>
    </div>
  );
};


export default NoticiaGeralDia;