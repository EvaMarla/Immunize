package br.com.immunize.navigationdrawer.NAVI.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.immunize.navigationdrawer.NAVI.Objects.Agenda;
import br.com.immunize.navigationdrawer.NAVI.Objects.Alimentacao;
import br.com.immunize.navigationdrawer.NAVI.Objects.Cartao;
import br.com.immunize.navigationdrawer.NAVI.Objects.Crianca;
import br.com.immunize.navigationdrawer.NAVI.Objects.Sintomas;
import br.com.immunize.navigationdrawer.NAVI.Objects.Vacina;
import br.com.immunize.navigationdrawer.R;

/**
 * Created by Marla on 30/07/2016.
 */
public class Utils {

    private static ArrayList<Alimentacao> alimentos;
    private static ArrayList<Sintomas> sintomas;
    private static ArrayList<Cartao> cartoes;
    private static ArrayList<Vacina> vacinas;
    private static ArrayList<Agenda> agenda;

    public static ArrayList<Alimentacao> alimentosList(Context ctx) {
        alimentos = new ArrayList<Alimentacao>();
        alimentos.add(new Alimentacao("Amamentação"));
        alimentos.add(new Alimentacao("Frutas frescas"));
        alimentos.add(new Alimentacao("Legumes"));
        alimentos.add(new Alimentacao("Verduras"));
        alimentos.add(new Alimentacao("Papa"));
        alimentos.add(new Alimentacao("Sopa"));
        alimentos.add(new Alimentacao("Mingau"));
        alimentos.add(new Alimentacao("Guloseimas"));
        alimentos.add(new Alimentacao("Iogurte"));
        alimentos.add(new Alimentacao("Suco"));
        alimentos.add(new Alimentacao("Chá"));
        alimentos.add(new Alimentacao("Água"));
        return alimentos;
    }

    public static ArrayList<Sintomas> sintomasList(Context ctx) {
        sintomas = new ArrayList<Sintomas>();
        sintomas.add(new Sintomas("Temperatura alta (febre)"));
        sintomas.add(new Sintomas("Temperatura baixa"));
        sintomas.add(new Sintomas("Perda de apetite"));
        sintomas.add(new Sintomas("Irritabilidade"));
        sintomas.add(new Sintomas("Diarreia"));
        sintomas.add(new Sintomas("Prisão de ventre"));
        sintomas.add(new Sintomas("Secreção nasal"));
        sintomas.add(new Sintomas("Tosse com secreção"));
        sintomas.add(new Sintomas("Vômito"));
        sintomas.add(new Sintomas("Queda de cabelo"));
        sintomas.add(new Sintomas("Olhos avermelhados"));
        sintomas.add(new Sintomas("Inchaço nas amígdalas"));
        sintomas.add(new Sintomas("Manchas na pele"));
        return sintomas;
    }

    public static ArrayList<Cartao> cartoesList(Context ctx) {
            cartoes = new ArrayList<Cartao>();
            cartoes.add(new Cartao(Cartao.ID_AO_NASCER, ctx.getString(R.string.ao_nascer)));
            cartoes.add(new Cartao(Cartao.ID_DOIS_MESES, ctx.getString(R.string.dois_meses)));
            cartoes.add(new Cartao(Cartao.ID_TRES_MESES, ctx.getString(R.string.tres_meses)));
            cartoes.add(new Cartao(Cartao.ID_QUATRO_MESES, ctx.getString(R.string.quatro_meses)));
            cartoes.add(new Cartao(Cartao.ID_CINCO_MESES, ctx.getString(R.string.cinco_meses)));
            cartoes.add(new Cartao(Cartao.ID_SEIS_MESES, ctx.getString(R.string.seis_meses)));
            cartoes.add(new Cartao(Cartao.ID_NOVE_MESES, ctx.getString(R.string.nove_meses)));
            cartoes.add(new Cartao(Cartao.ID_DOZE_MESES, ctx.getString(R.string.doze_meses)));
            cartoes.add(new Cartao(Cartao.ID_QUINZE_MESES, ctx.getString(R.string.quinze_meses)));
            return cartoes;
    }


    public static ArrayList<Agenda> agendaList(Context ctx) {
        agenda = new ArrayList<Agenda>();
        agenda.add(new Agenda("Escrever"));
        agenda.add(new Agenda("Alimentação"));
        agenda.add(new Agenda("Sintomas"));
        agenda.add(new Agenda("Remédios"));
        agenda.add(new Agenda("Peso"));
        agenda.add(new Agenda("Temperatura"));

        return agenda;
    }

    public static String getNomeAlimentacaoById(long id, Context ctx){
        alimentos = alimentosList(ctx);
        for(int i = 0; i < alimentos.size(); i++)
        {
            if(alimentos.get(i).getId() == id)
            {
                return alimentos.get(i).getPeriodo();
            }
        }
        return null;
    }

    public static String getNomeCaderninhoById(long id, Context ctx){
        agenda = agendaList(ctx);
        for(int i = 0; i < agenda.size(); i++)
        {
            if(agenda.get(i).getId() == id)
            {
                return agenda.get(i).getPeriodo();
            }
        }
        return null;
    }

    public static String getNomeSintomasById(long id, Context ctx){
        sintomas = sintomasList(ctx);
        for(int i = 0; i < sintomas.size(); i++)
        {
            if(sintomas.get(i).getId() == id)
            {
                return sintomas.get(i).getPeriodo();
            }
        }
        return null;
    }
    public static String getNomeCartaoById(long id, Context ctx){
        cartoes = cartoesList(ctx);

        for(int i = 0; i < cartoes.size(); i++){
            if(cartoes.get(i).getId() == id){
                return cartoes.get(i).getPeriodo();
            }
        }

        return null;
    }

    public static void setComeu(Context ctx, Alimentacao alimento, boolean comeu) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(alimento.getId() + Alimentacao.COMEU_ID, comeu);
        boolean comeuS = editor.commit();
        //alimento.setComeu(comeu);
    }

    public static void setSintoma(Context ctx, Sintomas sintomas, boolean sintoma) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(sintomas.getId() + Alimentacao.COMEU_ID, sintoma);
        boolean comeuS = editor.commit();
        //sintomas.setSintomas(sintoma);
    }


    public static boolean getComeuStatus(Context ctx, Alimentacao alimento) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        boolean comeuStatus = prefs.getBoolean(alimento.getId() + Alimentacao.COMEU_ID, false);
        return comeuStatus;
    }
    public static boolean getSintomaStatus(Context ctx, Sintomas sintomas) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        boolean sintomaStatus = prefs.getBoolean(sintomas.getId() + Sintomas.SINTOMA_ID, false);
        return sintomaStatus;
    }


    public static ArrayList<Vacina> vacinasList(Context ctx) {

        vacinas = new ArrayList<Vacina>();

        //Ao Nascer
        vacinas.add(new Vacina(Cartao.ID_AO_NASCER, Vacina.VACINA_ID_1, "BCG", ctx.getResources().getString(R.string.desc_aonascer_bcg)));
        vacinas.add(new Vacina(Cartao.ID_AO_NASCER, Vacina.VACINA_ID_2, "Hepatite B",ctx.getResources().getString(R.string.desc_aonascer_hepatiteb)));

        //Dois meses
        vacinas.add(new Vacina(Cartao.ID_DOIS_MESES, Vacina.VACINA_ID_3, "Penta/DTP - Dose 1", ctx.getResources().getString(R.string.desc_doismeses_pentadtp_primeiradose)));
        vacinas.add(new Vacina(Cartao.ID_DOIS_MESES, Vacina.VACINA_ID_4, "VIP/VOP - Dose 1", ctx.getResources().getString(R.string.desc_doismeses_vipvop_primeiradose)));
        vacinas.add(new Vacina(Cartao.ID_DOIS_MESES, Vacina.VACINA_ID_5, "Pneumocócica 10V Conjugada-Dose 1", ctx.getResources().getString(R.string.desc_doismeses_pneumococica_primeiradose)));
        vacinas.add(new Vacina(Cartao.ID_DOIS_MESES, Vacina.VACINA_ID_6, "Rotavírus Humano - Dose 1",  ctx.getResources().getString(R.string.desc_doismeses_rotavirushumano_primeiradose)));

        //Tres meses
        vacinas.add(new Vacina(Cartao.ID_TRES_MESES, Vacina.VACINA_ID_7, "Meningocócica C Conjugada - Dose 1", ctx.getResources().getString(R.string.desc_tresmeses_meningococica_primeiradose)));

        //Quatro meses
        vacinas.add(new Vacina(Cartao.ID_QUATRO_MESES, Vacina.VACINA_ID_8, "Penta/DTP - Dose 2", ctx.getResources().getString(R.string.desc_quatromeses_pentadtp_segundadose)));
        vacinas.add(new Vacina(Cartao.ID_QUATRO_MESES, Vacina.VACINA_ID_9, "VIP/VOP - Dose 2", ctx.getResources().getString(R.string.desc_quatromeses_vipvop_segundadose)));
        vacinas.add(new Vacina(Cartao.ID_QUATRO_MESES, Vacina.VACINA_ID_10, "Pneumocócica 10V Conjugada - Dose 2", ctx.getResources().getString(R.string.desc_quatromeses_pneumococica_segundadose)));
        vacinas.add(new Vacina(Cartao.ID_QUATRO_MESES, Vacina.VACINA_ID_11, "Rotavírus Humano - Dose 2", ctx.getResources().getString(R.string.desc_quatromeses_rotavirushumano_segundadose)));

        //Cinco mesess
        vacinas.add(new Vacina(Cartao.ID_CINCO_MESES, Vacina.VACINA_ID_12, "Meningocócica C Conjugada-Dose 2", ctx.getResources().getString(R.string.desc_cincomeses_meningococica_segundadose)));

        //Seis meses
        vacinas.add(new Vacina(Cartao.ID_SEIS_MESES, Vacina.VACINA_ID_13, "Penta/DTP - Dose 3", ctx.getResources().getString(R.string.desc_seismeses_pentadtp_terceiradose)));
        vacinas.add(new Vacina(Cartao.ID_SEIS_MESES, Vacina.VACINA_ID_14, "VIP/VOP - Dose 3", ctx.getResources().getString(R.string.desc_seismeses_vipvop_terceiradose)));

        //Nove meses
        vacinas.add(new Vacina(Cartao.ID_NOVE_MESES, Vacina.VACINA_ID_15, "Febre Amarela (dose única)", ctx.getResources().getString(R.string.desc_novemeses_febreamarela)));

        // Doze meses
        vacinas.add(new Vacina(Cartao.ID_DOZE_MESES, Vacina.VACINA_ID_16, "Pneumocócica 10V Conjugada-Reforço", ctx.getResources().getString(R.string.desc_dozemeses_pneumococica_reforco)));
        vacinas.add(new Vacina(Cartao.ID_DOZE_MESES, Vacina.VACINA_ID_17, "Meningocócica C Conjugada - Reforço", ctx.getResources().getString(R.string.desc_dozemeses_meningococica_reforco)));
        vacinas.add(new Vacina(Cartao.ID_DOZE_MESES, Vacina.VACINA_ID_18, "Triplice Viral - Dose 1", ctx.getResources().getString(R.string.desc_dozemeses_tripliceviral_primeiradose)));

        //Quinze meses
        vacinas.add(new Vacina(Cartao.ID_QUINZE_MESES, Vacina.VACINA_ID_19, "Penta/DTP - Primeiro Reforço com DTP", ctx.getResources().getString(R.string.desc_quinzemeses_pentadtp_reforco)));
        vacinas.add(new Vacina(Cartao.ID_QUINZE_MESES, Vacina.VACINA_ID_20, "VIP/VOP - Primeiro Reforço com VOP", ctx.getResources().getString(R.string.desc_quinzemeses_vipvop_reforco)));
        vacinas.add(new Vacina(Cartao.ID_QUINZE_MESES, Vacina.VACINA_ID_21, "Hepatite A - Dose Única", ctx.getResources().getString(R.string.desc_quinzemeses_hepatitea_doseunica)));
        vacinas.add(new Vacina(Cartao.ID_QUINZE_MESES, Vacina.VACINA_ID_22, "Tetra Vira - Dose Única",ctx.getResources().getString(R.string.desc_quinzemeses_tetraviral_doseunica)));

        return vacinas;

    }

    public static ArrayList<Vacina> getVacinasByIdCartao(long idCartao, Context Legendavacina) {
        ArrayList<Vacina> vacinasList = Utils.vacinasList(Legendavacina);
        ArrayList<Vacina> vacinasReturn = new ArrayList<>();

        for (int i = 0; i < vacinasList.size(); i++) {
            if (idCartao == vacinasList.get(i).getIdCartao()) {
                vacinasReturn.add(vacinasList.get(i));
            }
        }

        return vacinasReturn;
    }

    public static void setTomouVacina(Context ctx, Vacina vacina, boolean tomouVacina) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(vacina.getId() + Vacina.VACINA_FOI_TOMADA_ID, tomouVacina);
        boolean foiInserida = editor.commit();
        vacina.setFoiTomada(tomouVacina);
    }

    public static boolean getVacinaStatus(Context ctx, Vacina vacina) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        boolean vacinaStatus = prefs.getBoolean(vacina.getId() + Vacina.VACINA_FOI_TOMADA_ID, false);
        return vacinaStatus;
    }

   public static Vacina getProximaVacinaASerTomada(Context ctx) {
        List<Vacina> vacinas = vacinasList(ctx);

        Calendar criancaBornDate = getCriancaBornDate(ctx);

        Calendar dataAtual = Calendar.getInstance();

        long mesAtual = dataAtual.get(Calendar.MONTH);

        long mesCriancaNasceu = criancaBornDate.get(Calendar.MONTH);

        long mesPeriodo = mesAtual - mesCriancaNasceu;

        for (int i = 0; i < vacinas.size(); i++)
        {

            boolean vacinaFoiTomada = getVacinaStatus(ctx, vacinas.get(i));

            if(!vacinaFoiTomada && vacinas.get(i).getIdCartao() > mesPeriodo) {
                return vacinas.get(i);
            }
        }
        return null;
    }

 public static Calendar getCriancaBornDate(Context ctx) {
        Calendar calendar = Calendar.getInstance();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);

        int bornDayOfMonth = prefs.getInt(Crianca.CRIANCA_NASC_DIA_KEY, -2);
        int bornMonth = prefs.getInt(Crianca.CRIANCA_NASC_MES_KEY, -2);
        int bornYear = prefs.getInt(Crianca.CRIANCA_NASC_ANO_KEY, -2);

        calendar.set(Calendar.DAY_OF_MONTH, bornDayOfMonth);
        calendar.set(Calendar.MONTH, bornMonth);
        calendar.set(Calendar.YEAR, bornYear);

        return calendar;
    }

    public static boolean temCrianca(Context ctx){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        String id = prefs.getString(Crianca.CRIANCA_ID_KEY, null);

        if(id == null || !id.equals(Crianca.CRIANCA_ID_KEY)){
            return false;
        }

        return true;
    }
}
