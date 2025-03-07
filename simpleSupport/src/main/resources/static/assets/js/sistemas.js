$(document).ready(function () {
    paginacaoTabela('tabelaSistemas')
});

document.getElementById("pesquisaNomeSistema").addEventListener("keyup", filtrarTabela);
document.getElementById("pesquisaVersaoSistema").addEventListener("keyup", filtrarTabela);
document.getElementById("selectSistemaAtivo").addEventListener("change", filtrarTabela);

function filtrarTabela() {
    var nomeFilter = document.getElementById("pesquisaNomeSistema").value.toLowerCase();
    var versaoFilter = document.getElementById("pesquisaVersaoSistema").value.toLowerCase();
    var ativoFilter = document.getElementById("selectSistemaAtivo").value.toLowerCase();

    var table = document.querySelector(".table");
    var rows = table.querySelectorAll("tbody tr");

    rows.forEach(function(row) {
        var nome = row.querySelector("td:nth-child(2)").textContent.toLowerCase();
        var versao = row.querySelector("td:nth-child(4)").textContent.toLowerCase();
        var ativo = row.querySelector("td:nth-child(6)").textContent.toLowerCase();

        var nomeMatch = nome.indexOf(nomeFilter) > -1 || nomeFilter === "";
        var versaoMatch = versao.indexOf(versaoFilter) > -1 || versaoFilter === "";
        var ativoMatch = ativo.indexOf(ativoFilter) > -1 || ativoFilter === "";


        if (nomeMatch && versaoMatch && ativoMatch) {
            row.style.display = "";
        } else {
            row.style.display = "none";
        }
    });
}

function modalNovoSistema() {
    $('#modalNovoSistema').modal('show');
}

function novoSistema() {
    var nomeSistema = document.getElementById('nomeSistema').value;
    var descricaoSistema = document.getElementById('descricaoSistema').value;
    var versaoSistema = document.getElementById('versaoSistema').value;
    var categoriaSistema = document.getElementById('categoriaSistema').value;
    var checkSistemaAtivo = document.getElementById('checkSistemaAtivo').checked;

    if (!nomeSistema || !descricaoSistema || !versaoSistema) {
        Swal.fire({
            title: "Ops!",
            text: "Preencha os campos obrigatórios!",
            icon: "warning",
            confirmButtonText: 'OK'
        })
        return;
    }

    $.ajax({
        url: '/empresa/salvarsistema',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            nome: nomeSistema,
            descricao: descricaoSistema,
            versao: versaoSistema,
            categoria: categoriaSistema,
            ativo: checkSistemaAtivo
        }),
        complete: function(xhr, status) {
            switch (xhr.status) {
                case 200:
                    Swal.fire({
                        title: "Pronto!",
                        text: "Sistema salvo com sucesso!",
                        icon: "success",
                        confirmButtonText: 'OK'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            location.reload();
                        }
                    });
                    break;
                case 500:
                    Swal.fire({
                        title: "Erro!",
                        text: "Ocorreu um erro ao salvar esse sistema.",
                        icon: "error"
                    });
                    break;
                default:
                    alert("Erro desconhecido: " + status);
            }
        }
    });
}

function removerSistema(button) {
    var idSistema = button.getAttribute('data-id');
    Swal.fire({
        title: 'Tem certeza que deseja remover este sistema?',
        text: "Essa ação é irreversível!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Sim, remover.',
        cancelButtonText: 'Não, voltar.',
        reverseButtons: true
    }).then((result) => {
        if (result.isConfirmed) {
            deletarSistema(idSistema);
        } else {
            Swal.fire('Cancelado', 'O sistema não foi removido', 'info');
        }
    });
}

function deletarSistema(idSistema) {
    $.ajax({
        url: '/empresa/removersistema',
        type: 'POST',
        data: {
            idSistema: idSistema
        },
        success: function (response) {
            Swal.fire({
                title: "Pronto!",
                text: "Sistema removido com sucesso.",
                icon: "success",
                confirmButtonText: 'OK'
            }).then((result) => {
                if (result.isConfirmed) {
                    location.reload();
                }
            });
        },
        error: function (xhr, status, error) {
            Swal.fire({
                title: "Ops!",
                text: "Erro ao remover sistema",
                icon: "error",
                confirmButtonText: "OK"
            });
        }
    });
}