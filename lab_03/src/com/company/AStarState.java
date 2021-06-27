package com.company;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class AStarState
{
    /** Это ссылка на карту, по которой перемещается алгоритм A * **/
    private Map2D map;


    // инициализирует карту всех открытых путевых точек
    private Map<Location, Waypoint> open_waypoints
            = new HashMap<Location, Waypoint>();

    // инициализирует карту всех закрытых путевых точек
    private Map<Location, Waypoint> closed_waypoints
            = new HashMap<Location, Waypoint> ();


    /**
     * Инициализировать новый объект состояния для использования алгоритма поиска пути A *.
     **/
    public AStarState(Map2D map)
    {
        if (map == null)
            throw new NullPointerException("map cannot be null");

        this.map = map;
    }

    /** Возвращает карту, по которой перемещается поисковик A* **/
    public Map2D getMap()
    {
        return map;
    }


    public Waypoint getMinOpenWaypoint()
    {
        //если нет открытых точек возращаем null
        if (numOpenWaypoints() == 0)
            return null;

        // Инициализируем все открытые точки, находим лучшие точки и их стоимость
        Set open_waypoint_keys = open_waypoints.keySet();
        Iterator i = open_waypoint_keys.iterator();
        Waypoint best = null;
        float best_cost = Float.MAX_VALUE;

        //Сканируем все откытые точки
        while (i.hasNext())
        {
            //Сканируем тукущее местоположение
            Location location = (Location)i.next();
            //Сохраняем текущую точку маршрута
            Waypoint waypoint = open_waypoints.get(location);
            //Сохраняем обзую стоимость текущей путевой точке
            float waypoint_total_cost = waypoint.getTotalCost();

            //Если стоимость меньше, то заменяем как лучшую
            if (waypoint_total_cost < best_cost)
            {
                best = open_waypoints.get(location);
                best_cost = waypoint_total_cost;
            }

        }
        //Возвращаем путь с минимальной стоимостью
        return best;
    }


    public boolean addOpenWaypoint(Waypoint newWP)
    {
        //Находим положение новой путевой точки
        Location location = newWP.getLocation();

        //Проверяем были ли мы на этой точке.
        if (open_waypoints.containsKey(location))
        {
            //Если точка уже была, проверяем дешевли ли путь или нет
            Waypoint current_waypoint = open_waypoints.get(location);
            if (newWP.getPreviousCost() < current_waypoint.getPreviousCost())
            {
                //Если стоимость меньше у новой, то заменяем и возращаем true
                open_waypoints.put(location, newWP);
                return true;
            }
            // Если больше, возвращаем false
            return false;
        }
        //Если ее еще не было, то добовляем и возвращаем true
        open_waypoints.put(location, newWP);
        return true;
    }

    /** Возвращает текущее количество открытых путевых точек. **/
    public int numOpenWaypoints()
    {
        return open_waypoints.size();
    }


    /**
     * Этот метод перемещает путевую точку в указанном месте из
     * открытый список к закрытому списку.
     **/
    public void closeWaypoint(Location loc)
    {
        Waypoint waypoint = open_waypoints.remove(loc);
        closed_waypoints.put(loc, waypoint);
    }

    /**
     * Возвращает истину, если коллекция закрытых путевых точек содержит путевую точку.
     * для указанного места.
     **/
    public boolean isLocationClosed(Location loc)
    {
        return closed_waypoints.containsKey(loc);
    }
}

