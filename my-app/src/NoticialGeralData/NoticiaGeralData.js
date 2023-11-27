import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

const NoticiaGeralData = ({ token, selectedDate }) => {
  const [noticias, setNoticias] = useState([]);

  useEffect(() => {
    const fetchNoticias = async () => {
      try {
        const noticiasResponse = await fetch(`http://localhost:8080/v1/noticias/usuario/todas-as-noticias-do-usuario/com-data/?data=${selectedDate}`, {
          method: 'GET',
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
        });

        if (noticiasResponse.ok) {
          const noticiasData = await noticiasResponse.json();
          setNoticias(noticiasData.items);
        } else {
          console.error('Erro ao buscar notícias:', noticiasResponse.status);
        }
      } catch (error) {
        console.error('Erro durante a solicitação de notícias:', error);
      }
    };

    if (token && selectedDate) {
      fetchNoticias();
    }
  }, [token, selectedDate]);

  return (
    <div className="container mt-4">
      <div className="noticia-square">
        {noticias.length > 0 ? (
          noticias.map((noticia) => (
            <div key={noticia.id} className="card mb-3">
              <div className="card-body">
                <h2 className="card-title">{noticia.titulo}</h2>
                <p className="card-text">{noticia.introducao}</p>
                <p>Data de Publicação: {noticia.data_publicacao}</p>
                <p>Editorias: {noticia.editorias}</p>
              </div>
              <hr className="my-0" />
            </div>
          ))
        ) : (
          <p className="alert alert-info">Não há notícias na data selecionada!</p>
        )}
        <Link to="/etiquetas" className='btn btn-success'>
          Voltar
        </Link>
      </div>
    </div>
  );
};

export default NoticiaGeralData;