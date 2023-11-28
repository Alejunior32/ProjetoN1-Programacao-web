import React, { useState, useEffect } from 'react';
import './NoticiaEtiqueta.css';
import { Link, useParams } from 'react-router-dom';

const NoticiaEtiquetas = ({ token }) => {
  const [noticias, setNoticias] = useState([]);
  const {idEtiqueta} = useParams();

  useEffect(() => {
    const fetchNoticias = async () => {
      try {
        const noticiasResponse = await fetch(`http://localhost:8080/v1/noticias/usuario/data-atual/?etiquetaId=${idEtiqueta}`, {
            method: 'GET',
            headers: {
              'Authorization': `Bearer ${token}`,
              'Content-Type': 'application/json',
            },
          });
  
          if (noticiasResponse.ok) {
            const noticiasData = await noticiasResponse.json();
            if (noticiasData) {
              setNoticias(noticiasData);
            } else {
              console.log('Dados da API estão incompletos:', noticiasData);
            }
          } else {
            console.error('Erro ao buscar notícias:', noticiasResponse.status);
          }
        } catch (error) {
          console.error('Erro durante a solicitação de notícias:', error);
        }
      };
  
      if (token && idEtiqueta) {
        fetchNoticias();
      }
    }, [token, idEtiqueta]);


    return (
      <div className="container mt-4 d-flex justify-content-center align-items-center">
        <div className="noticia-etiqueta-square">
          <div className="d-flex justify-content-between align-items-center">
            <h2>Notícias da Data Selecionada</h2>
            <Link to="/etiquetas" className="btn btn-success">
              Voltar
            </Link>
          </div>
          {noticias.length > 0 ? (
            noticias.items.map((noticia) => (
              <div key={noticia.titulo} className="card mb-3">
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
        </div>
      </div>
    );
  }


export default NoticiaEtiquetas;